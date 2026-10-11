package com.example.data.model

data class CompanyData(
    val ticker: String,
    val name: String,
    val marketCapUsd: Double,
    val debtUsd: Double,
    val debtGhs: Double = 0.0,
    val debtToCapPercent: Double,
    val netCapitalization: Double,
    val isDistressed: Boolean,
    val country: String,
    val sector: String,
    val cik: String = "",
    val sourceUrl: String = "",
    val lastUpdated: String = "",
    val rank: Int = 1,
    val annualSavingsUsd: Double = 0.0,
    val fee10PctUsd: Double = 0.0,
    val isLiveFetched: Boolean = false,
    val errorNotice: String? = null,
    val tokenSymbol: String = ""
)

data class SecCompanyFacts(
    val liabilitiesUsd: Double,
    val sharesOutstanding: Double = 0.0,
    val entityName: String = ""
)

data class FxRates(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

data class WorldBankIndicator(
    val countryCode: String,
    val countryName: String,
    val value: Double?,
    val date: String,
    val indicatorCode: String
)

data class GhanaMacroData(
    val totalGhs: Double = 733.9, // GH¢733.9B
    val totalUsd: Double = 62.8,  // $62.8B
    val domesticGhs: Double = 396.7, // GH¢396.7B
    val externalUsd: Double = 28.8,  // $28.8B
    val externalGhs: Double = 337.2, // GH¢337.2B
    val debtToGdp: Double = 45.9,    // 45.9%
    val gdpGhs: Double = 1597.1,     // GH¢1,597.1B
    val period: String = "Sept 2026",
    val source: String = "Bank of Ghana / World Bank DT.DOD.DECT.CD",
    val gfimVolumeGhs: Double = 49.0, // GFIM GH¢49.0B volume
    val gfimValueGhs: Double = 43.2,
    val gfimTrades: Int = 36124,
    val tbillBidsGhs: Double = 8.20,
    val tbillAcceptedGhs: Double = 7.21
)

data class CbgProfile(
    val name: String = "Consolidated Bank Ghana (CBG)",
    val totalLiabilitiesGhs: Double = 14.14, // GH¢14.14B (GH¢14,140,916k)
    val depositsGhs: Double = 12.27,        // GH¢12.27B
    val borrowedGhs: Double = 0.892,        // GH¢892.8M
    val equityGhs: Double = 1.36,           // GH¢1.36B
    val retainedEarningsGhs: Double = -1.90, // -GH¢1.90B
    val nplRatio: Double = 12.50,           // 12.50% (improved from 15.03%)
    val pbt2024Ghs: Double = 0.1601,        // Profit Before Tax GH¢160.1M (vs -GH¢712.9M)
    val assetGrowthPct: Double = 21.5,
    val liquidAssetsToDeposits: Double = 111.68,
    val recapPlanGhs: Double = 1.0          // GH¢1B recapitalization
)

data class Invoice(
    val id: String,
    val creditorTin: String,
    val debtorTin: String,
    val creditorName: String,
    val debtorName: String,
    val amountUsd: Double = 10000.0, // FIXED $10k per spec
    val daysOverdue: Int = 0,
    val discountPercent: Double = 0.0,
    val salePriceUsd: Double = 10000.0,
    val status: InvoiceStatus = InvoiceStatus.LISTED,
    val pickupCode: String = "",
    val tokenizedRefCode: String = ""
)

enum class InvoiceStatus {
    LISTED, SOLD, NETTED
}

data class NettingCircle(
    val id: String,
    val cyclePath: List<String>,
    val invoices: List<Invoice>,
    val totalVolumeUsd: Double,
    val cashMovedUsd: Double = 0.0, // Always $0 moved!
    val status: String = "DETECTED",
    val feePercent: Double = 2.0,
    val feeAmountUsd: Double = 600.0,
    val platformShareUsd: Double = 480.0, // 80%
    val referrerShareUsd: Double = 120.0, // 20%
    val digitalConsentGiven: Boolean = false,
    val nettingAgreementId: String = ""
)

data class RefinancingTermSheet(
    val refCode: String,
    val ticker: String,
    val companyName: String,
    val marketCapUsd: Double,
    val debtRealUsd: Double,
    val debtToCapRatioPct: Double,
    val netCapUsd: Double,
    val oldRatePct: Double,
    val newRatePct: Double,
    val annualSavingsUsd: Double,
    val fee10PctUsd: Double,
    val upfrontDemoGhs: Double = 2500.0,
    val status: String = "ISSUED", // ISSUED / REVOKED
    val validUntilEpochMs: Long,
    val authMsisdn: String = "",
    val qrPayload: String = "",
    val isTokenized: Boolean = true,
    val tokenSymbol: String = ""
)

data class GseQuote(
    val symbol: String,
    val name: String,
    val price: Double,
    val change: Double,
    val volume: Long,
    val marketCapGhs: Double
)

// FLOW D: TOKENIZED DEBT (BASE L2) — 1-FOR-1 BACKED BY REAL ASSIGNMENT DOC
data class TokenizedDebt(
    val refCode: String,
    val ticker: String,
    val tokenName: String,
    val tokenSymbol: String,
    val totalSupply: Long = 10000L,
    val tokenPriceUsd: Double = 600.0,
    val faceValueUsd: Double = 1000.0,
    val discountPercent: Double = 40.0,
    val contractAddress: String = "0x8972C6B249E0F6B56d3570F2B0135d2FeD93c108",
    val usdcContract: String = "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913",
    val backingDocUrl: String = "https://orcaatrate.com/verify/",
    val backingDocHash: String = "SHA256:e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
    val chainId: Long = 8453L, // Base Mainnet
    val status: String = "MINTED", // MINTED, TRADING, SOLD
    val buyerWallet: String = "",
    val txHash: String = "0x9f5a7d23a1c849e7b23118cf9420b9e81b67f10b2498e72c8172948a2741be40",
    val createdAt: String = "2026-10-10 23:00 UTC",
    val availableTokens: Long = 8420L
)

data class BaseL2Status(
    val chainId: Long = 8453L,
    val blockNumberDecimal: Long = 52450631L,
    val blockNumberHex: String = "0x3205547",
    val usdcContract: String = "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913",
    val rpcUrl: String = "https://mainnet.base.org",
    val isOnline: Boolean = true
)
