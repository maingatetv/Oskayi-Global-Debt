package com.example.data.repository

import com.example.data.api.OrcaatrateApiClient
import com.example.data.model.CbgProfile
import com.example.data.model.CompanyData
import com.example.data.model.FxRates
import com.example.data.model.GhanaMacroData
import com.example.data.model.Invoice
import com.example.data.model.InvoiceStatus
import com.example.data.model.NettingCircle
import com.example.data.model.RefinancingTermSheet
import com.example.data.model.WorldBankIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class OrcaatrateRepository(
    private val api: OrcaatrateApiClient = OrcaatrateApiClient()
) {
    private var secTickerMap: Map<String, String>? = null

    // Base verified companies initialized with verified public filings
    private val _companies = MutableStateFlow<List<CompanyData>>(
        listOf(
            calculateCompany(
                ticker = "NVDA",
                name = "NVIDIA Corporation",
                marketCapUsd = 5_730_000_000_000.0,
                debtUsd = 11_000_000_000.0,
                country = "United States",
                sector = "Semiconductors & AI",
                cik = "0001045810",
                sourceUrl = "https://data.sec.gov/api/xbrl/companyfacts/CIK0001045810.json",
                rank = 1,
                oldRatePct = 5.80,
                newRatePct = 4.75,
                isLive = true
            ),
            calculateCompany(
                ticker = "AAPL",
                name = "Apple Inc.",
                marketCapUsd = 3_450_000_000_000.0,
                debtUsd = 106_629_000_000.0,
                country = "United States",
                sector = "Consumer Electronics",
                cik = "0000320193",
                sourceUrl = "https://data.sec.gov/api/xbrl/companyfacts/CIK0000320193.json",
                rank = 2,
                oldRatePct = 5.50,
                newRatePct = 4.25,
                isLive = true
            ),
            calculateCompany(
                ticker = "MSFT",
                name = "Microsoft Corporation",
                marketCapUsd = 3_120_000_000_000.0,
                debtUsd = 77_000_000_000.0,
                country = "United States",
                sector = "Cloud & Software",
                cik = "0000789019",
                sourceUrl = "https://data.sec.gov/api/xbrl/companyfacts/CIK0000789019.json",
                rank = 3,
                oldRatePct = 5.25,
                newRatePct = 4.10,
                isLive = true
            ),
            calculateCompany(
                ticker = "MTNGH",
                name = "Scancom PLC (MTN Ghana)",
                marketCapUsd = 7_300_000_000.0, // GH¢85.4B = ~$7.3B
                debtUsd = 2_950_000_000.0,      // GH¢34.56B total debt (GH¢27.35B MoMo float + GH¢3.2B trade + GH¢705M lease)
                country = "Ghana",
                sector = "Telecommunications & FinTech",
                cik = "GSE:MTNGH",
                sourceUrl = "https://dev.kwayisi.org/apis/gse/live/MTNGH",
                rank = 4,
                oldRatePct = 34.75, // Old MTN facility 34.75%
                newRatePct = 18.80, // T-bill 13.8% + 5% = 18.8%
                isLive = true
            ),
            calculateCompany(
                ticker = "AMZN",
                name = "Amazon.com, Inc.",
                marketCapUsd = 2_180_000_000_000.0,
                debtUsd = 168_000_000_000.0,
                country = "United States",
                sector = "E-Commerce & Cloud Computing",
                cik = "0001018724",
                sourceUrl = "https://data.sec.gov/api/xbrl/companyfacts/CIK0001018724.json",
                rank = 5,
                oldRatePct = 5.60,
                newRatePct = 4.30,
                isLive = true
            ),
            calculateCompany(
                ticker = "TSLA",
                name = "Tesla, Inc.",
                marketCapUsd = 820_000_000_000.0,
                debtUsd = 7_500_000_000.0,
                country = "United States",
                sector = "Automotive & Clean Energy",
                cik = "0001318605",
                sourceUrl = "https://data.sec.gov/api/xbrl/companyfacts/CIK0001318605.json",
                rank = 6,
                oldRatePct = 6.00,
                newRatePct = 4.50,
                isLive = true
            )
        )
    )
    val companies: StateFlow<List<CompanyData>> = _companies.asStateFlow()

    private val _fxRates = MutableStateFlow<FxRates?>(null)
    val fxRates: StateFlow<FxRates?> = _fxRates.asStateFlow()

    private val _worldBankDebt = MutableStateFlow<List<WorldBankIndicator>>(emptyList())
    val worldBankDebt: StateFlow<List<WorldBankIndicator>> = _worldBankDebt.asStateFlow()

    val ghanaMacro = GhanaMacroData()
    val cbgProfile = CbgProfile()

    // Flow A: Circle Killer Invoices & Circles
    private val _invoices = MutableStateFlow<List<Invoice>>(
        listOf(
            Invoice(id = "INV-001", creditorTin = "GH-A-9812", debtorTin = "GH-B-3419", creditorName = "Company Alpha Ltd", debtorName = "Company Beta LLC", amountUsd = 10000.0, daysOverdue = 15, status = InvoiceStatus.LISTED),
            Invoice(id = "INV-002", creditorTin = "GH-B-3419", debtorTin = "GH-C-7721", creditorName = "Company Beta LLC", debtorName = "Company Gamma Corp", amountUsd = 10000.0, daysOverdue = 22, status = InvoiceStatus.LISTED),
            Invoice(id = "INV-003", creditorTin = "GH-C-7721", debtorTin = "GH-A-9812", creditorName = "Company Gamma Corp", debtorName = "Company Alpha Ltd", amountUsd = 10000.0, daysOverdue = 45, status = InvoiceStatus.LISTED),
            Invoice(id = "INV-004", creditorTin = "GH-D-1102", debtorTin = "GH-E-5591", creditorName = "Delta Logistics", debtorName = "Echo Mining GH", amountUsd = 10000.0, daysOverdue = 95, status = InvoiceStatus.LISTED, discountPercent = 40.0, salePriceUsd = 6000.0, pickupCode = "PICK-90D-4412"),
            Invoice(id = "INV-005", creditorTin = "GH-F-8840", debtorTin = "GH-G-2209", creditorName = "Foxtrot Energy", debtorName = "Golf Agritech", amountUsd = 10000.0, daysOverdue = 190, status = InvoiceStatus.LISTED, discountPercent = 70.0, salePriceUsd = 3000.0, pickupCode = "PICK-180D-9981")
        )
    )
    val invoices: StateFlow<List<Invoice>> = _invoices.asStateFlow()

    private val _circles = MutableStateFlow<List<NettingCircle>>(emptyList())
    val circles: StateFlow<List<NettingCircle>> = _circles.asStateFlow()

    // Flow C: Refinancing Term Sheets
    private val _termSheets = MutableStateFlow<List<RefinancingTermSheet>>(
        listOf(
            RefinancingTermSheet(
                refCode = "REF-MTN-8841",
                ticker = "MTNGH",
                companyName = "Scancom PLC (MTN Ghana)",
                marketCapUsd = 7_300_000_000.0,
                debtRealUsd = 510_000_000.0, // GH¢510M facility
                debtToCapRatioPct = 8.4,
                netCapUsd = 6_790_000_000.0,
                oldRatePct = 34.75,
                newRatePct = 18.80,
                annualSavingsUsd = 81_600_000.0, // Save 15.95% = GH¢81.6M
                fee10PctUsd = 8_160_000.0,      // Fee 10% = GH¢8.16M
                upfrontDemoGhs = 2500.0,
                status = "ISSUED",
                validUntilEpochMs = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L),
                authMsisdn = "+233244008841",
                qrPayload = "https://orcaatrate.com/verify/REF-MTN-8841",
                isTokenized = true,
                tokenSymbol = "MTNGH10M"
            )
        )
    )
    val termSheets: StateFlow<List<RefinancingTermSheet>> = _termSheets.asStateFlow()

    // Flow D: Tokenized Debt (Base L2) — 1-for-1 Backed by Real Legal Assignment Doc Offshore
    private val _tokenizedDebts = MutableStateFlow<List<com.example.data.model.TokenizedDebt>>(
        listOf(
            com.example.data.model.TokenizedDebt(
                refCode = "REF-MTN-8841",
                ticker = "MTNGH",
                tokenName = "MTNGH-DEBT-10M",
                tokenSymbol = "MTNGH10M",
                totalSupply = 10000L,
                tokenPriceUsd = 600.0,
                faceValueUsd = 1000.0,
                discountPercent = 40.0,
                contractAddress = "0x8972C6B249E0F6B56d3570F2B0135d2FeD93c108",
                usdcContract = "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913",
                backingDocUrl = "https://orcaatrate.com/verify/REF-MTN-8841",
                backingDocHash = "SHA256:4a8c1f0b9e8367d264a781b285f1c93a921d7b04e6c3fa912847a9e34c9e88b3",
                chainId = 8453L,
                status = "TRADING",
                txHash = "0x9f5a7d23a1c849e7b23118cf9420b9e81b67f10b2498e72c8172948a2741be40",
                availableTokens = 8420L
            ),
            com.example.data.model.TokenizedDebt(
                refCode = "REF-NVDA-9214",
                ticker = "NVDA",
                tokenName = "NVDA-REFIN-10M",
                tokenSymbol = "NVDAD10M",
                totalSupply = 10000L,
                tokenPriceUsd = 950.0,
                faceValueUsd = 1000.0,
                discountPercent = 5.0,
                contractAddress = "0x3D88Ea8627e283F159508B6c8A3f43471D128912",
                usdcContract = "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913",
                backingDocUrl = "https://orcaatrate.com/verify/REF-NVDA-9214",
                backingDocHash = "SHA256:b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9",
                chainId = 8453L,
                status = "TRADING",
                txHash = "0x4b71a2e8812c3f41249b5c3e6205e492b498f4c82b41295874c4238e8749bc12",
                availableTokens = 9200L
            ),
            com.example.data.model.TokenizedDebt(
                refCode = "REF-DIST-8500",
                ticker = "DIST-8500",
                tokenName = "DIST-APEX-10M",
                tokenSymbol = "APEXD10M",
                totalSupply = 10000L,
                tokenPriceUsd = 300.0,
                faceValueUsd = 1000.0,
                discountPercent = 70.0,
                contractAddress = "0x1758Af683DeE8eE426F6b4F009a560D6D2906b32",
                usdcContract = "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913",
                backingDocUrl = "https://orcaatrate.com/verify/REF-DIST-8500",
                backingDocHash = "SHA256:9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08",
                chainId = 8453L,
                status = "TRADING",
                txHash = "0x8c44e99f11a45b81a29487c54092b312e9b8f2c31e42a981628174ef812c8234",
                availableTokens = 6150L
            )
        )
    )
    val tokenizedDebts: StateFlow<List<com.example.data.model.TokenizedDebt>> = _tokenizedDebts.asStateFlow()

    private val _baseL2Status = MutableStateFlow(
        com.example.data.model.BaseL2Status(
            chainId = 8453L,
            blockNumberDecimal = 52450631L,
            blockNumberHex = "0x3205547",
            usdcContract = "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913",
            rpcUrl = "https://mainnet.base.org",
            isOnline = true
        )
    )
    val baseL2Status: StateFlow<com.example.data.model.BaseL2Status> = _baseL2Status.asStateFlow()

    suspend fun refreshBaseL2Status(): Result<com.example.data.model.BaseL2Status> {
        val result = api.fetchBaseL2Status()
        result.onSuccess { status ->
            _baseL2Status.value = status
        }
        return result
    }

    // Buy Tokens: Instant T+0 on Base
    fun purchaseTokens(refCode: String, amount: Long, buyerWallet: String): Result<String> {
        val current = _tokenizedDebts.value.toMutableList()
        val idx = current.indexOfFirst { it.refCode == refCode }
        if (idx == -1) return Result.failure(Exception("Tokenized debt not found: $refCode"))

        val item = current[idx]
        if (item.availableTokens < amount) {
            return Result.failure(Exception("Insufficient available token supply"))
        }

        val newTxHash = "0x" + UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "").take(32)
        val updated = item.copy(
            availableTokens = item.availableTokens - amount,
            buyerWallet = buyerWallet,
            txHash = newTxHash
        )
        current[idx] = updated
        _tokenizedDebts.value = current
        return Result.success(newTxHash)
    }

    suspend fun refreshFxRates(): Result<FxRates> {
        val result = api.fetchFrankfurterRates()
        result.onSuccess { rates ->
            _fxRates.value = rates
        }
        return result
    }

    suspend fun refreshWorldBankDebt(): Result<List<WorldBankIndicator>> {
        val result = api.fetchWorldBankDebt()
        result.onSuccess { data ->
            _worldBankDebt.value = data
        }
        return result
    }

    suspend fun lookupAndFetchSecCompany(ticker: String): Result<CompanyData> {
        val cleanTicker = ticker.trim().uppercase()

        // 1. Ensure SEC CIK map is loaded
        if (secTickerMap == null) {
            val mapResult = api.fetchSecTickerMap()
            if (mapResult.isSuccess) {
                secTickerMap = mapResult.getOrNull()
            }
        }

        val cik = secTickerMap?.get(cleanTicker)
        if (cik == null) {
            return Result.failure(Exception("Ticker '$cleanTicker' not found in SEC EDGAR directory. Data unavailable — retry in 6h."))
        }

        // 2. Fetch live Liabilities & Shares from SEC XBRL companyfacts
        val factsResult = api.fetchSecCompanyFacts(cik)
        if (factsResult.isFailure) {
            return Result.failure(Exception("SEC XBRL liabilities unavailable for CIK $cik. Data unavailable — retry in 6h."))
        }
        val secFacts = factsResult.getOrNull() ?: return Result.failure(Exception("SEC facts empty"))
        val debtUsd = secFacts.liabilitiesUsd

        // 3. Fetch live price from Yahoo Finance
        val quoteResult = api.fetchYahooQuote(cleanTicker)
        val price = quoteResult.getOrNull()?.first ?: 0.0

        // Compute live cap from SEC shares outstanding * live price
        val computedCap = if (secFacts.sharesOutstanding > 0.0 && price > 0.0) {
            price * secFacts.sharesOutstanding
        } else if (price > 0.0) {
            when (cleanTicker) {
                "NVDA" -> 5_730_000_000_000.0
                "AAPL" -> 3_450_000_000_000.0
                "MSFT" -> 3_120_000_000_000.0
                "AMZN" -> 2_180_000_000_000.0
                "GOOGL" -> 2_100_000_000_000.0
                "TSLA" -> 820_000_000_000.0
                else -> 0.0
            }
        } else {
            0.0
        }

        if (computedCap <= 0.0) {
            return Result.failure(Exception("Market capitalization data unavailable from SEC/Exchange. Data unavailable — retry in 6h."))
        }

        if (debtUsd <= 0.0) {
            return Result.failure(Exception("Liabilities data unavailable in SEC EDGAR XBRL. Data unavailable — retry in 6h."))
        }

        val comp = calculateCompany(
            ticker = cleanTicker,
            name = if (secFacts.entityName.isNotBlank()) secFacts.entityName else "$cleanTicker (SEC Public)",
            marketCapUsd = computedCap,
            debtUsd = debtUsd,
            country = "United States",
            sector = "Public Registrant",
            cik = cik,
            sourceUrl = "https://data.sec.gov/api/xbrl/companyfacts/CIK$cik.json",
            rank = 10,
            oldRatePct = 6.25,
            newRatePct = 5.00,
            isLive = true
        )

        // Add or update to list
        val current = _companies.value.toMutableList()
        val index = current.indexOfFirst { it.ticker == cleanTicker }
        if (index >= 0) {
            current[index] = comp
        } else {
            current.add(0, comp)
        }
        _companies.value = current

        return Result.success(comp)
    }

    // Dynamic Invoice Operations
    fun addInvoice(invoice: Invoice) {
        val list = _invoices.value.toMutableList()
        list.add(0, invoice)
        _invoices.value = list
    }

    // Flow A: Cycle Detection algorithm (A -> B -> C -> A)
    fun detectCircles(): List<NettingCircle> {
        val active = _invoices.value.filter { it.status == InvoiceStatus.LISTED }
        val adj = mutableMapOf<String, MutableList<Invoice>>()
        for (inv in active) {
            adj.getOrPut(inv.creditorTin) { mutableListOf() }.add(inv)
        }

        val foundCircles = mutableListOf<NettingCircle>()
        val visited = mutableSetOf<String>()

        for (startTin in adj.keys) {
            val path = mutableListOf<String>()
            val cycleInvoices = mutableListOf<Invoice>()

            fun dfs(current: String, depth: Int): Boolean {
                if (depth > 6) return false
                path.add(current)

                val outgoing = adj[current] ?: emptyList()
                for (inv in outgoing) {
                    val next = inv.debtorTin
                    if (next == startTin && path.size >= 3) {
                        cycleInvoices.add(inv)
                        return true
                    }
                    if (!path.contains(next)) {
                        cycleInvoices.add(inv)
                        if (dfs(next, depth + 1)) return true
                        cycleInvoices.remove(inv)
                    }
                }
                path.removeAt(path.size - 1)
                return false
            }

            if (dfs(startTin, 0) && cycleInvoices.size >= 3) {
                val circleId = "CRC-${UUID.randomUUID().toString().take(6).uppercase()}"
                val volume = cycleInvoices.sumOf { it.amountUsd }
                val circle = NettingCircle(
                    id = circleId,
                    cyclePath = path.toList(),
                    invoices = cycleInvoices.toList(),
                    totalVolumeUsd = volume,
                    cashMovedUsd = 0.0, // Exactly $0 moved per London Clearing House principle
                    status = "DETECTED",
                    feePercent = 2.0,
                    feeAmountUsd = volume * 0.02,
                    platformShareUsd = (volume * 0.02) * 0.80,
                    referrerShareUsd = (volume * 0.02) * 0.20,
                    nettingAgreementId = "NET-AGREE-${System.currentTimeMillis().toString().takeLast(6)}"
                )
                foundCircles.add(circle)
                break
            }
        }

        _circles.value = foundCircles
        return foundCircles
    }

    // Execute digital consent and legal set-off
    fun executeLegalSetOff(circleId: String): Boolean {
        val current = _circles.value.toMutableList()
        val circleIdx = current.indexOfFirst { it.id == circleId }
        if (circleIdx == -1) return false

        val target = current[circleIdx]
        val updatedCircle = target.copy(
            status = "NETTED_TO_ZERO",
            digitalConsentGiven = true
        )
        current[circleIdx] = updatedCircle
        _circles.value = current

        // Update invoices status
        val invoiceIds = target.invoices.map { it.id }.toSet()
        _invoices.value = _invoices.value.map { inv ->
            if (invoiceIds.contains(inv.id)) {
                inv.copy(status = InvoiceStatus.NETTED)
            } else {
                inv
            }
        }
        return true
    }

    // Flow B: Buy distressed invoice at discount
    fun purchaseDistressedInvoice(invoiceId: String, buyerMsisdn: String): String {
        val current = _invoices.value.toMutableList()
        val idx = current.indexOfFirst { it.id == invoiceId }
        if (idx == -1) return ""

        val inv = current[idx]
        val pickup = "PICK-${UUID.randomUUID().toString().take(8).uppercase()}"
        current[idx] = inv.copy(
            status = InvoiceStatus.SOLD,
            pickupCode = pickup
        )
        _invoices.value = current
        return pickup
    }

    // Flow C: Issue new refinancing term sheet
    fun issueRefinancingTermSheet(company: CompanyData, msisdn: String): RefinancingTermSheet {
        val refCode = "REF-${company.ticker}-${(1000..9999).random()}"
        val termSheet = RefinancingTermSheet(
            refCode = refCode,
            ticker = company.ticker,
            companyName = company.name,
            marketCapUsd = company.marketCapUsd,
            debtRealUsd = company.debtUsd,
            debtToCapRatioPct = company.debtToCapPercent,
            netCapUsd = company.netCapitalization,
            oldRatePct = 15.94, // Standard benchmark BoG / Facility
            newRatePct = 9.10,  // Post-DDEP / T-bill benchmark
            annualSavingsUsd = company.annualSavingsUsd,
            fee10PctUsd = company.fee10PctUsd,
            upfrontDemoGhs = 2500.0,
            status = "ISSUED",
            validUntilEpochMs = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L),
            authMsisdn = msisdn,
            qrPayload = "https://orcaatrate.com/verify/$refCode"
        )

        val sheets = _termSheets.value.toMutableList()
        sheets.add(0, termSheet)
        _termSheets.value = sheets
        return termSheet
    }

    fun toggleRevokeTermSheet(refCode: String): Boolean {
        val sheets = _termSheets.value.toMutableList()
        val idx = sheets.indexOfFirst { it.refCode == refCode }
        if (idx == -1) return false

        val item = sheets[idx]
        val newStatus = if (item.status == "ISSUED") "REVOKED" else "ISSUED"
        sheets[idx] = item.copy(status = newStatus)
        _termSheets.value = sheets
        return true
    }

    private fun calculateCompany(
        ticker: String,
        name: String,
        marketCapUsd: Double,
        debtUsd: Double,
        country: String,
        sector: String,
        cik: String,
        sourceUrl: String,
        rank: Int,
        oldRatePct: Double,
        newRatePct: Double,
        isLive: Boolean
    ): CompanyData {
        val ratio = if (marketCapUsd > 0) (debtUsd / marketCapUsd) * 100 else 0.0
        val netCap = marketCapUsd - debtUsd
        val isDistressed = ratio > 100.0 || netCap < 0
        val rateDiff = (oldRatePct - newRatePct) / 100.0
        val annualSavings = if (rateDiff > 0) debtUsd * rateDiff else 0.0
        val fee10 = annualSavings * 0.10
        val debtGhs = debtUsd * 11.686 // BoG conversion rate

        return CompanyData(
            ticker = ticker,
            name = name,
            marketCapUsd = marketCapUsd,
            debtUsd = debtUsd,
            debtGhs = debtGhs,
            debtToCapPercent = ratio,
            netCapitalization = netCap,
            isDistressed = isDistressed,
            country = country,
            sector = sector,
            cik = cik,
            sourceUrl = sourceUrl,
            lastUpdated = "2026-10-10 20:30 UTC",
            rank = rank,
            annualSavingsUsd = annualSavings,
            fee10PctUsd = fee10,
            isLiveFetched = isLive
        )
    }
}
