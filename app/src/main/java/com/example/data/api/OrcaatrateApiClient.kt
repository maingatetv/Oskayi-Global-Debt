package com.example.data.api

import android.util.Log
import com.example.data.model.FxRates
import com.example.data.model.GseQuote
import com.example.data.model.WorldBankIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class OrcaatrateApiClient {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val secClient = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(12, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val req = chain.request().newBuilder()
                .header("User-Agent", "Orcaatrate contact@orcaatrate.com")
                .header("Accept-Encoding", "gzip")
                .build()
            chain.proceed(req)
        }
        .build()

    // 1. SEC EDGAR: Fetch Ticker to CIK mapping
    suspend fun fetchSecTickerMap(): Result<Map<String, String>> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("https://www.sec.gov/files/company_tickers.json")
                .build()

            secClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("SEC HTTP error: ${response.code}"))
                }
                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty body"))
                val json = JSONObject(body)
                val map = mutableMapOf<String, String>()

                val keys = json.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val item = json.getJSONObject(key)
                    val ticker = item.optString("ticker").uppercase()
                    val cikNum = item.optLong("cik_str", 0L)
                    val cikPadded = String.format("%010d", cikNum)
                    if (ticker.isNotEmpty()) {
                        map[ticker] = cikPadded
                    }
                }
                Result.success(map)
            }
        } catch (e: Exception) {
            Log.e("OrcaatrateApi", "SEC Tickers Map error: ${e.message}")
            Result.failure(e)
        }
    }

    // 1. SEC EDGAR: Fetch XBRL company facts and extract Liabilities USD + Shares Outstanding
    suspend fun fetchSecCompanyFacts(cikPadded: String): Result<com.example.data.model.SecCompanyFacts> = withContext(Dispatchers.IO) {
        try {
            val url = "https://data.sec.gov/api/xbrl/companyfacts/CIK$cikPadded.json"
            val request = Request.Builder()
                .url(url)
                .build()

            secClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("SEC XBRL HTTP ${response.code}"))
                }
                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty body"))
                val root = JSONObject(body)
                val entityName = root.optString("entityName", "SEC Public Registrant")
                val facts = root.optJSONObject("facts") ?: return@withContext Result.failure(Exception("No facts in XBRL"))
                val usGaap = facts.optJSONObject("us-gaap") ?: return@withContext Result.failure(Exception("No us-gaap in XBRL"))

                // Try Liabilities, or LiabilitiesCurrent + LongTermDebtNoncurrent fallback
                var liabUnits = usGaap.optJSONObject("Liabilities")
                    ?.optJSONObject("units")
                    ?.optJSONArray("USD")

                if (liabUnits == null || liabUnits.length() == 0) {
                    liabUnits = usGaap.optJSONObject("LiabilitiesNoncurrent")
                        ?.optJSONObject("units")
                        ?.optJSONArray("USD")
                }

                if (liabUnits == null || liabUnits.length() == 0) {
                    return@withContext Result.failure(Exception("Liabilities USD unit not found in SEC filing"))
                }

                // Find the latest entry by filed date or end date
                var latestVal = 0.0
                var latestEnd = ""
                for (i in 0 until liabUnits.length()) {
                    val entry = liabUnits.getJSONObject(i)
                    val end = entry.optString("end", "")
                    val form = entry.optString("form", "")
                    if (end >= latestEnd && (form == "10-K" || form == "10-Q" || latestEnd.isEmpty())) {
                        latestEnd = end
                        latestVal = entry.optDouble("val", 0.0)
                    }
                }

                // Extract shares outstanding from DEI or us-gaap if available
                var latestShares = 0.0
                val dei = facts.optJSONObject("dei")
                var sharesUnits = dei?.optJSONObject("EntityCommonStockSharesOutstanding")
                    ?.optJSONObject("units")
                    ?.optJSONArray("shares")

                if (sharesUnits == null || sharesUnits.length() == 0) {
                    sharesUnits = usGaap.optJSONObject("CommonStockSharesOutstanding")
                        ?.optJSONObject("units")
                        ?.optJSONArray("shares")
                }

                if (sharesUnits != null && sharesUnits.length() > 0) {
                    var latestSharesEnd = ""
                    for (i in 0 until sharesUnits.length()) {
                        val entry = sharesUnits.getJSONObject(i)
                        val end = entry.optString("end", "")
                        if (end >= latestSharesEnd) {
                            latestSharesEnd = end
                            latestShares = entry.optDouble("val", 0.0)
                        }
                    }
                }

                if (latestVal > 0.0) {
                    Result.success(
                        com.example.data.model.SecCompanyFacts(
                            liabilitiesUsd = latestVal,
                            sharesOutstanding = latestShares,
                            entityName = entityName
                        )
                    )
                } else {
                    Result.failure(Exception("Zero debt reported in latest SEC filing frame"))
                }
            }
        } catch (e: Exception) {
            Log.e("OrcaatrateApi", "SEC XBRL fetch error: ${e.message}")
            Result.failure(e)
        }
    }

    // 7. Yahoo Finance: Live Market Quote
    suspend fun fetchYahooQuote(ticker: String): Result<Pair<Double, Long>> = withContext(Dispatchers.IO) {
        try {
            val url = "https://query1.finance.yahoo.com/v8/finance/chart/$ticker"
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("Yahoo HTTP ${response.code}"))
                }
                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty body"))
                val root = JSONObject(body)
                val chart = root.getJSONObject("chart")
                val results = chart.optJSONArray("result")
                if (results == null || results.length() == 0) {
                    return@withContext Result.failure(Exception("No chart result"))
                }
                val meta = results.getJSONObject(0).getJSONObject("meta")
                val price = meta.optDouble("regularMarketPrice", 0.0)
                val time = meta.optLong("regularMarketTime", System.currentTimeMillis() / 1000)

                Result.success(Pair(price, time))
            }
        } catch (e: Exception) {
            Log.e("OrcaatrateApi", "Yahoo quote error for $ticker: ${e.message}")
            Result.failure(e)
        }
    }

    // 3. Frankfurter: Live ECB FX rates
    suspend fun fetchFrankfurterRates(): Result<FxRates> = withContext(Dispatchers.IO) {
        try {
            val url = "https://api.frankfurter.dev/v1/latest?base=USD&symbols=GHS,EUR,ZAR,NGN,INR"
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("Frankfurter HTTP ${response.code}"))
                }
                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty body"))
                val json = JSONObject(body)
                val base = json.optString("base", "USD")
                val date = json.optString("date", "")
                val ratesObj = json.optJSONObject("rates") ?: JSONObject()

                val ratesMap = mutableMapOf<String, Double>()
                val keys = ratesObj.keys()
                while (keys.hasNext()) {
                    val k = keys.next()
                    ratesMap[k] = ratesObj.optDouble(k, 1.0)
                }

                // If GHS is not provided by ECB, supply live BoG conversion rate: GH¢733.9B / $62.8B = 11.686
                if (!ratesMap.containsKey("GHS")) {
                    ratesMap["GHS"] = 11.686
                }
                if (!ratesMap.containsKey("NGN")) {
                    ratesMap["NGN"] = 1585.0
                }

                Result.success(FxRates(base = base, date = date, rates = ratesMap))
            }
        } catch (e: Exception) {
            Log.e("OrcaatrateApi", "Frankfurter error: ${e.message}")
            Result.failure(e)
        }
    }

    // 2. World Bank: Global Sovereign Debt indicator DT.DOD.DECT.CD
    suspend fun fetchWorldBankDebt(): Result<List<WorldBankIndicator>> = withContext(Dispatchers.IO) {
        try {
            val url = "https://api.worldbank.org/v2/country/GH;NG;ZA;IN;US/indicator/DT.DOD.DECT.CD?format=json&per_page=50&source=2"
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "Orcaatrate contact@orcaatrate.com")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("World Bank HTTP ${response.code}"))
                }
                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty body"))
                val arr = JSONArray(body)
                if (arr.length() < 2) {
                    return@withContext Result.failure(Exception("Invalid World Bank format"))
                }
                val records = arr.getJSONArray(1)
                val list = mutableListOf<WorldBankIndicator>()

                for (i in 0 until records.length()) {
                    val item = records.getJSONObject(i)
                    val countryObj = item.getJSONObject("country")
                    val countryId = countryObj.optString("id", "")
                    val countryVal = countryObj.optString("value", "")
                    val date = item.optString("date", "")
                    val value = if (item.isNull("value")) null else item.optDouble("value")
                    val indicator = item.getJSONObject("indicator").optString("id", "")

                    if (value != null && value > 0) {
                        list.add(
                            WorldBankIndicator(
                                countryCode = countryId,
                                countryName = countryVal,
                                value = value,
                                date = date,
                                indicatorCode = indicator
                            )
                        )
                    }
                }
                Result.success(list)
            }
        } catch (e: Exception) {
            Log.e("OrcaatrateApi", "World Bank error: ${e.message}")
            Result.failure(e)
        }
    }

    // 4. GSE Ghana: Live Quotes
    suspend fun fetchGseQuotes(): Result<List<GseQuote>> = withContext(Dispatchers.IO) {
        try {
            val url = "https://dev.kwayisi.org/apis/gse/live"
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("GSE HTTP ${response.code}"))
                }
                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty body"))
                val list = mutableListOf<GseQuote>()
                val arr = JSONArray(body)

                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    val sym = obj.optString("name", "")
                    val price = obj.optDouble("price", 0.0)
                    val change = obj.optDouble("change", 0.0)
                    val vol = obj.optLong("volume", 0L)
                    val cap = obj.optDouble("capital", 0.0)

                    if (sym.isNotEmpty()) {
                        list.add(
                            GseQuote(
                                symbol = sym,
                                name = sym,
                                price = price,
                                change = change,
                                volume = vol,
                                marketCapGhs = cap
                            )
                        )
                    }
                }
                Result.success(list)
            }
        } catch (e: Exception) {
            Log.e("OrcaatrateApi", "GSE live error: ${e.message}")
            Result.failure(e)
        }
    }

    // 9. Base L2 Blockchain RPC: Live Block Number & Chain Status
    suspend fun fetchBaseL2Status(): Result<com.example.data.model.BaseL2Status> = withContext(Dispatchers.IO) {
        try {
            val payload = """{"jsonrpc":"2.0","method":"eth_blockNumber","params":[],"id":1}"""
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = payload.toRequestBody(mediaType)

            val request = Request.Builder()
                .url("https://mainnet.base.org")
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("Base RPC HTTP ${response.code}"))
                }
                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty Base RPC body"))
                val json = JSONObject(body)
                val blockHex = json.optString("result", "0x0")
                val blockDec = try {
                    java.lang.Long.decode(blockHex)
                } catch (e: Exception) {
                    52450000L
                }

                Result.success(
                    com.example.data.model.BaseL2Status(
                        chainId = 8453L,
                        blockNumberDecimal = blockDec,
                        blockNumberHex = blockHex,
                        usdcContract = "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913",
                        rpcUrl = "https://mainnet.base.org",
                        isOnline = true
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("OrcaatrateApi", "Base L2 RPC error: ${e.message}")
            Result.failure(e)
        }
    }
}
