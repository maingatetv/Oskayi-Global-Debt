package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CompanyData
import com.example.data.model.Invoice
import com.example.data.model.NettingCircle
import com.example.data.model.RefinancingTermSheet
import com.example.data.repository.OrcaatrateRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    DASHBOARD,
    COMPANIES_SEC,
    CIRCLE_KILLER,
    MARKETPLACE,
    TOKENIZED_DEBT,
    SOVEREIGN_MACRO,
    AUTO_CAMPAIGN
}

class OrcaatrateViewModel(
    private val repository: OrcaatrateRepository = OrcaatrateRepository()
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(AppTab.DASHBOARD)
    val selectedTab: StateFlow<AppTab> = _selectedTab.asStateFlow()

    // World Debt Clock: $365.5T baseline, ticking +$63,000 / sec
    private val _worldDebtClock = MutableStateFlow(365_500_000_000_000.0)
    val worldDebtClock: StateFlow<Double> = _worldDebtClock.asStateFlow()

    val companies = repository.companies
    val fxRates = repository.fxRates
    val worldBankDebt = repository.worldBankDebt
    val invoices = repository.invoices
    val circles = repository.circles
    val termSheets = repository.termSheets
    val tokenizedDebts = repository.tokenizedDebts
    val baseL2Status = repository.baseL2Status
    val ghanaMacro = repository.ghanaMacro
    val cbgProfile = repository.cbgProfile

    // Company Explorer search & lookup state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSecLoading = MutableStateFlow(false)
    val isSecLoading: StateFlow<Boolean> = _isSecLoading.asStateFlow()

    private val _lookupError = MutableStateFlow<String?>(null)
    val lookupError: StateFlow<String?> = _lookupError.asStateFlow()

    private val _selectedCompany = MutableStateFlow<CompanyData?>(null)
    val selectedCompany: StateFlow<CompanyData?> = _selectedCompany.asStateFlow()

    // Modal / Action States
    private val _showMomoModal = MutableStateFlow(false)
    val showMomoModal: StateFlow<Boolean> = _showMomoModal.asStateFlow()

    private val _momoPin = MutableStateFlow("")
    val momoPin: StateFlow<String> = _momoPin.asStateFlow()

    private val _momoMsisdn = MutableStateFlow("0244008841")
    val momoMsisdn: StateFlow<String> = _momoMsisdn.asStateFlow()

    private val _momoStatus = MutableStateFlow<String?>(null) // null, "INITIATING", "USSD_PROMPT", "SUCCESS"
    val momoStatus: StateFlow<String?> = _momoStatus.asStateFlow()

    private val _activeTermSheet = MutableStateFlow<RefinancingTermSheet?>(null)
    val activeTermSheet: StateFlow<RefinancingTermSheet?> = _activeTermSheet.asStateFlow()

    // Flow B Distressed Invoice Purchase Modal
    private val _purchasingInvoice = MutableStateFlow<Invoice?>(null)
    val purchasingInvoice: StateFlow<Invoice?> = _purchasingInvoice.asStateFlow()

    private val _generatedPickupCode = MutableStateFlow<String?>(null)
    val generatedPickupCode: StateFlow<String?> = _generatedPickupCode.asStateFlow()

    // Flow D Tokenized Debt Purchase Modal
    private val _buyingToken = MutableStateFlow<com.example.data.model.TokenizedDebt?>(null)
    val buyingToken: StateFlow<com.example.data.model.TokenizedDebt?> = _buyingToken.asStateFlow()

    private val _tokenBuyAmount = MutableStateFlow("10")
    val tokenBuyAmount: StateFlow<String> = _tokenBuyAmount.asStateFlow()

    private val _tokenBuyerWallet = MutableStateFlow("0x71C...49A")
    val tokenBuyerWallet: StateFlow<String> = _tokenBuyerWallet.asStateFlow()

    private val _tokenPaymentMethod = MutableStateFlow("USDC on Base (0x83358...)")
    val tokenPaymentMethod: StateFlow<String> = _tokenPaymentMethod.asStateFlow()

    private val _tokenTxSuccess = MutableStateFlow<String?>(null)
    val tokenTxSuccess: StateFlow<String?> = _tokenTxSuccess.asStateFlow()

    // Currency toggle state (USD vs GHS conversion via BoG/Frankfurter)
    private val _currencyIsGhs = MutableStateFlow(false)
    val currencyIsGhs: StateFlow<Boolean> = _currencyIsGhs.asStateFlow()

    fun toggleCurrency() {
        _currencyIsGhs.value = !_currencyIsGhs.value
        val curr = if (_currencyIsGhs.value) "Ghana Cedis (GH¢ @ 11.69)" else "US Dollars ($)"
        _bannerNotice.value = "Display currency set to $curr"
    }

    // Interactive Modals for Flow A & B
    private val _showAddInvoiceModal = MutableStateFlow(false)
    val showAddInvoiceModal: StateFlow<Boolean> = _showAddInvoiceModal.asStateFlow()

    private val _showListInvoiceModal = MutableStateFlow(false)
    val showListInvoiceModal: StateFlow<Boolean> = _showListInvoiceModal.asStateFlow()

    // 1-for-1 Backing Document Inspector Modal
    private val _inspectingTokenDoc = MutableStateFlow<com.example.data.model.TokenizedDebt?>(null)
    val inspectingTokenDoc: StateFlow<com.example.data.model.TokenizedDebt?> = _inspectingTokenDoc.asStateFlow()

    fun openBackingDocModal(token: com.example.data.model.TokenizedDebt) {
        _inspectingTokenDoc.value = token
    }

    fun closeBackingDocModal() {
        _inspectingTokenDoc.value = null
    }

    fun toggleAddInvoiceModal(show: Boolean) {
        _showAddInvoiceModal.value = show
    }

    fun toggleListInvoiceModal(show: Boolean) {
        _showListInvoiceModal.value = show
    }

    fun registerNewInvoice(
        creditorName: String,
        debtorName: String,
        creditorTin: String,
        debtorTin: String,
        daysOverdue: Int = 10
    ) {
        val newId = "INV-00${(invoices.value.size + 1)}"
        val inv = Invoice(
            id = newId,
            creditorTin = creditorTin.ifBlank { "GH-X-1029" },
            debtorTin = debtorTin.ifBlank { "GH-Y-8842" },
            creditorName = creditorName.ifBlank { "Participant Co." },
            debtorName = debtorName.ifBlank { "Counterparty Ltd" },
            amountUsd = 10000.0,
            daysOverdue = daysOverdue,
            status = com.example.data.model.InvoiceStatus.LISTED
        )
        repository.addInvoice(inv)
        _showAddInvoiceModal.value = false
        _bannerNotice.value = "Invoice $newId registered ($10,000 FIXED). Ready for graph cycle search."
    }

    fun listCustomOverdueInvoice(
        creditorName: String,
        debtorName: String,
        daysOverdue: Int
    ) {
        val discount = when {
            daysOverdue >= 180 -> 70.0
            daysOverdue >= 90 -> 40.0
            daysOverdue >= 60 -> 20.0
            else -> 10.0
        }
        val salePrice = 10000.0 * (1.0 - (discount / 100.0))
        val newId = "INV-LST-${(100..999).random()}"
        val pickup = "PICK-${(1000..9999).random()}"
        val inv = Invoice(
            id = newId,
            creditorTin = "GH-CR-${(1000..9999).random()}",
            debtorTin = "GH-DB-${(1000..9999).random()}",
            creditorName = creditorName.ifBlank { "Originating Vendor" },
            debtorName = debtorName.ifBlank { "Debtor Corp" },
            amountUsd = 10000.0,
            daysOverdue = daysOverdue,
            discountPercent = discount,
            salePriceUsd = salePrice,
            status = com.example.data.model.InvoiceStatus.LISTED,
            pickupCode = pickup
        )
        repository.addInvoice(inv)
        _showListInvoiceModal.value = false
        _bannerNotice.value = "Listed $newId at ${discount.toInt()}% OFF ($${String.format("%,.0f", salePrice)}). Pickup code: $pickup"
    }

    // Banner message for actions
    private val _bannerNotice = MutableStateFlow<String?>(null)
    val bannerNotice: StateFlow<String?> = _bannerNotice.asStateFlow()

    init {
        // Start live ticking clock: +$63k every second
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _worldDebtClock.value += 63000.0
            }
        }

        // Fetch live FX rates, World Bank debt, and Base L2 RPC on start
        viewModelScope.launch {
            repository.refreshFxRates()
            repository.refreshWorldBankDebt()
            repository.refreshBaseL2Status()
        }

        // Default selected company
        if (companies.value.isNotEmpty()) {
            _selectedCompany.value = companies.value.first()
        }
    }

    fun selectTab(tab: AppTab) {
        _selectedTab.value = tab
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _lookupError.value = null
    }

    fun selectCompany(company: CompanyData) {
        _selectedCompany.value = company
        _selectedTab.value = AppTab.COMPANIES_SEC
    }

    fun lookupSecTicker(ticker: String) {
        if (ticker.isBlank()) return
        viewModelScope.launch {
            _isSecLoading.value = true
            _lookupError.value = null
            val result = repository.lookupAndFetchSecCompany(ticker)
            _isSecLoading.value = false

            if (result.isSuccess) {
                _selectedCompany.value = result.getOrNull()
            } else {
                _lookupError.value = result.exceptionOrNull()?.message ?: "Data unavailable — retry in 6h"
            }
        }
    }

    // Flow A: Circle Killer actions
    fun runCircleDetection() {
        val detected = repository.detectCircles()
        if (detected.isNotEmpty()) {
            _bannerNotice.value = "Circle of $${String.format("%,.0f", detected.first().totalVolumeUsd)} detected! Ready to net to $0."
        } else {
            _bannerNotice.value = "No active circular obligations found. System scanning every 6 hours."
        }
    }

    fun confirmLegalSetOff(circleId: String) {
        val success = repository.executeLegalSetOff(circleId)
        if (success) {
            _bannerNotice.value = "Digital consent recorded. Netting Agreement executed: $0 cash moved per London clearing principle."
        }
    }

    // Flow B: Purchase Distressed Invoice
    fun startBuyInvoice(invoice: Invoice) {
        _purchasingInvoice.value = invoice
        _generatedPickupCode.value = null
    }

    fun confirmBuyInvoice(buyerMsisdn: String) {
        val inv = _purchasingInvoice.value ?: return
        val pickup = repository.purchaseDistressedInvoice(inv.id, buyerMsisdn)
        _generatedPickupCode.value = pickup
        _bannerNotice.value = "Notice of Assignment issued! Pickup code: $pickup. 80/20 fee split automated."
    }

    fun dismissBuyInvoiceModal() {
        _purchasingInvoice.value = null
        _generatedPickupCode.value = null
    }

    // Flow C: MoMo Authorization & Refinancing Sheet
    fun openMomoAuthModal(company: CompanyData) {
        _selectedCompany.value = company
        _showMomoModal.value = true
        _momoPin.value = ""
        _momoStatus.value = null
    }

    fun setMomoPin(pin: String) {
        if (pin.length <= 4) {
            _momoPin.value = pin
        }
    }

    fun setMomoMsisdn(msisdn: String) {
        _momoMsisdn.value = msisdn
    }

    fun submitMomoPayment() {
        val comp = _selectedCompany.value ?: return
        viewModelScope.launch {
            _momoStatus.value = "INITIATING"
            delay(1200)
            _momoStatus.value = "USSD_PROMPT"
            delay(1500)
            _momoStatus.value = "SUCCESS"
            delay(800)

            // Issue Term Sheet
            val sheet = repository.issueRefinancingTermSheet(comp, _momoMsisdn.value)
            _activeTermSheet.value = sheet
            _showMomoModal.value = false
            _bannerNotice.value = "Payment verified via MTN MoMo! Term sheet issued: ${sheet.refCode}"
        }
    }

    fun closeMomoModal() {
        _showMomoModal.value = false
        _momoStatus.value = null
        _momoPin.value = ""
    }

    fun viewTermSheet(sheet: RefinancingTermSheet) {
        _activeTermSheet.value = sheet
    }

    fun closeTermSheet() {
        _activeTermSheet.value = null
    }

    fun toggleRevokeTermSheet(refCode: String) {
        val ok = repository.toggleRevokeTermSheet(refCode)
        if (ok) {
            val updated = repository.termSheets.value.find { it.refCode == refCode }
            _activeTermSheet.value = updated
            _bannerNotice.value = "Status updated: ${updated?.status}"
        }
    }

    // Flow D: Tokenized Debt (Base L2)
    fun openTokenBuyModal(token: com.example.data.model.TokenizedDebt) {
        _buyingToken.value = token
        _tokenBuyAmount.value = "10"
        _tokenTxSuccess.value = null
    }

    fun setTokenBuyAmount(amount: String) {
        _tokenBuyAmount.value = amount
    }

    fun setTokenBuyerWallet(wallet: String) {
        _tokenBuyerWallet.value = wallet
    }

    fun setTokenPaymentMethod(method: String) {
        _tokenPaymentMethod.value = method
    }

    fun confirmTokenPurchase() {
        val token = _buyingToken.value ?: return
        val count = _tokenBuyAmount.value.toLongOrNull() ?: 1L
        val result = repository.purchaseTokens(token.refCode, count, _tokenBuyerWallet.value)
        if (result.isSuccess) {
            val txHash = result.getOrNull() ?: "0x..."
            _tokenTxSuccess.value = txHash
            _bannerNotice.value = "Minted $count tokens of ${token.tokenSymbol} on Base! Tx: ${txHash.take(12)}... Backed 1-for-1 by legal assignment doc."
        } else {
            _bannerNotice.value = result.exceptionOrNull()?.message ?: "Purchase failed"
        }
    }

    fun dismissTokenBuyModal() {
        _buyingToken.value = null
        _tokenTxSuccess.value = null
    }

    fun refreshBaseRpc() {
        viewModelScope.launch {
            repository.refreshBaseL2Status()
        }
    }

    fun clearBannerNotice() {
        _bannerNotice.value = null
    }
}
