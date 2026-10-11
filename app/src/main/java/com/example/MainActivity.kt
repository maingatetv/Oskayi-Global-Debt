package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.OrcaatrateViewModel
import com.example.ui.components.BuyInvoiceDialog
import com.example.ui.components.MomoAuthDialog
import com.example.ui.components.TermSheetDetailDialog
import com.example.ui.components.TokenBuyDialog
import com.example.ui.screens.AutoCampaignScreen
import com.example.ui.screens.CircleKillerScreen
import com.example.ui.screens.CompanyExplorerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MarketplaceScreen
import com.example.ui.screens.SovereignMacroScreen
import com.example.ui.screens.TokenizedDebtScreen
import com.example.ui.theme.CoralDistressed
import com.example.ui.theme.CyanElectric
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.EmeraldHealthy
import com.example.ui.theme.GoldLuminous
import com.example.ui.theme.GoldWarm
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavySurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextWhite

class MainActivity : ComponentActivity() {

    private val viewModel: OrcaatrateViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val selectedTab by viewModel.selectedTab.collectAsState()
                val worldDebt by viewModel.worldDebtClock.collectAsState()
                val companies by viewModel.companies.collectAsState()
                val selectedCompany by viewModel.selectedCompany.collectAsState()
                val fxRates by viewModel.fxRates.collectAsState()
                val worldBankDebt by viewModel.worldBankDebt.collectAsState()
                val invoices by viewModel.invoices.collectAsState()
                val circles by viewModel.circles.collectAsState()
                val tokenizedDebts by viewModel.tokenizedDebts.collectAsState()
                val baseL2Status by viewModel.baseL2Status.collectAsState()
                val ghanaMacro = viewModel.ghanaMacro
                val cbgProfile = viewModel.cbgProfile

                val searchQuery by viewModel.searchQuery.collectAsState()
                val isSecLoading by viewModel.isSecLoading.collectAsState()
                val lookupError by viewModel.lookupError.collectAsState()

                // Dialog states
                val showMomoModal by viewModel.showMomoModal.collectAsState()
                val momoMsisdn by viewModel.momoMsisdn.collectAsState()
                val momoPin by viewModel.momoPin.collectAsState()
                val momoStatus by viewModel.momoStatus.collectAsState()

                val activeTermSheet by viewModel.activeTermSheet.collectAsState()
                val purchasingInvoice by viewModel.purchasingInvoice.collectAsState()
                val generatedPickupCode by viewModel.generatedPickupCode.collectAsState()

                val buyingToken by viewModel.buyingToken.collectAsState()
                val tokenBuyAmount by viewModel.tokenBuyAmount.collectAsState()
                val tokenBuyerWallet by viewModel.tokenBuyerWallet.collectAsState()
                val tokenPaymentMethod by viewModel.tokenPaymentMethod.collectAsState()
                val tokenTxSuccess by viewModel.tokenTxSuccess.collectAsState()

                val showAddInvoiceModal by viewModel.showAddInvoiceModal.collectAsState()
                val showListInvoiceModal by viewModel.showListInvoiceModal.collectAsState()
                val inspectingTokenDoc by viewModel.inspectingTokenDoc.collectAsState()
                val currencyIsGhs by viewModel.currencyIsGhs.collectAsState()

                val bannerNotice by viewModel.bannerNotice.collectAsState()

                // Handle back button when in a sub-tab
                if (selectedTab != AppTab.DASHBOARD) {
                    BackHandler {
                        viewModel.selectTab(AppTab.DASHBOARD)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = DeepObsidian,
                    topBar = {
                        OrcaatrateTopAppBar(
                            selectedTab = selectedTab,
                            currencyIsGhs = currencyIsGhs,
                            onToggleCurrency = { viewModel.toggleCurrency() },
                            onTabSelect = { viewModel.selectTab(it) }
                        )
                    },
                    bottomBar = {
                        OrcaatrateBottomNav(
                            selectedTab = selectedTab,
                            circleCount = circles.size,
                            onTabSelect = { viewModel.selectTab(it) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // Main Tab Views
                        when (selectedTab) {
                            AppTab.DASHBOARD -> {
                                HomeScreen(
                                    worldDebt = worldDebt,
                                    companies = companies,
                                    fxRates = fxRates,
                                    ghanaMacro = ghanaMacro,
                                    currencyIsGhs = currencyIsGhs,
                                    onToggleCurrency = { viewModel.toggleCurrency() },
                                    onSelectCompany = { comp -> viewModel.selectCompany(comp) },
                                    onNavigateTab = { tab -> viewModel.selectTab(tab) },
                                    onOpenMomo = { comp -> viewModel.openMomoAuthModal(comp) }
                                )
                            }
                            AppTab.COMPANIES_SEC -> {
                                CompanyExplorerScreen(
                                    companies = companies,
                                    selectedCompany = selectedCompany,
                                    searchQuery = searchQuery,
                                    isLoadingSec = isSecLoading,
                                    lookupError = lookupError,
                                    onSearchChange = { viewModel.onSearchQueryChanged(it) },
                                    onLookupTicker = { viewModel.lookupSecTicker(it) },
                                    onSelectCompany = { comp -> viewModel.selectCompany(comp) },
                                    onOpenMomoAuth = { comp -> viewModel.openMomoAuthModal(comp) }
                                )
                            }
                            AppTab.CIRCLE_KILLER -> {
                                CircleKillerScreen(
                                    invoices = invoices,
                                    circles = circles,
                                    onRunDetection = { viewModel.runCircleDetection() },
                                    onExecuteSetOff = { circleId -> viewModel.confirmLegalSetOff(circleId) },
                                    onOpenAddInvoice = { viewModel.toggleAddInvoiceModal(true) }
                                )
                            }
                            AppTab.MARKETPLACE -> {
                                MarketplaceScreen(
                                    invoices = invoices,
                                    onBuyInvoice = { inv -> viewModel.startBuyInvoice(inv) },
                                    onOpenListInvoice = { viewModel.toggleListInvoiceModal(true) }
                                )
                            }
                            AppTab.TOKENIZED_DEBT -> {
                                TokenizedDebtScreen(
                                    tokenizedDebts = tokenizedDebts,
                                    baseStatus = baseL2Status,
                                    onBuyToken = { token -> viewModel.openTokenBuyModal(token) },
                                    onRefreshRpc = { viewModel.refreshBaseRpc() },
                                    onInspectDoc = { token -> viewModel.openBackingDocModal(token) }
                                )
                            }
                            AppTab.SOVEREIGN_MACRO -> {
                                SovereignMacroScreen(
                                    macro = ghanaMacro,
                                    cbg = cbgProfile,
                                    worldBankList = worldBankDebt
                                )
                            }
                            AppTab.AUTO_CAMPAIGN -> {
                                AutoCampaignScreen(selectedCompany = selectedCompany)
                            }
                        }

                        // Banner notification overlay
                        if (bannerNotice != null) {
                            Surface(
                                color = NavyCard,
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CyanElectric),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth()
                                    .testTag("app_banner_notice")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = bannerNotice ?: "",
                                        color = TextWhite,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(
                                        onClick = { viewModel.clearBannerNotice() },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }

                    // Modals
                    if (showMomoModal && selectedCompany != null) {
                        MomoAuthDialog(
                            company = selectedCompany!!,
                            msisdn = momoMsisdn,
                            pin = momoPin,
                            status = momoStatus,
                            onMsisdnChange = { viewModel.setMomoMsisdn(it) },
                            onPinChange = { viewModel.setMomoPin(it) },
                            onSubmit = { viewModel.submitMomoPayment() },
                            onDismiss = { viewModel.closeMomoModal() }
                        )
                    }

                    if (activeTermSheet != null) {
                        TermSheetDetailDialog(
                            termSheet = activeTermSheet!!,
                            onRevokeToggle = { refCode -> viewModel.toggleRevokeTermSheet(refCode) },
                            onDismiss = { viewModel.closeTermSheet() }
                        )
                    }

                    if (purchasingInvoice != null) {
                        BuyInvoiceDialog(
                            invoice = purchasingInvoice!!,
                            pickupCode = generatedPickupCode,
                            onConfirm = { msisdn -> viewModel.confirmBuyInvoice(msisdn) },
                            onDismiss = { viewModel.dismissBuyInvoiceModal() }
                        )
                    }

                    if (buyingToken != null) {
                        TokenBuyDialog(
                            token = buyingToken!!,
                            amountStr = tokenBuyAmount,
                            walletStr = tokenBuyerWallet,
                            paymentMethod = tokenPaymentMethod,
                            txSuccess = tokenTxSuccess,
                            onAmountChange = { viewModel.setTokenBuyAmount(it) },
                            onWalletChange = { viewModel.setTokenBuyerWallet(it) },
                            onPaymentMethodChange = { viewModel.setTokenPaymentMethod(it) },
                            onConfirm = { viewModel.confirmTokenPurchase() },
                            onDismiss = { viewModel.dismissTokenBuyModal() }
                        )
                    }

                    if (showAddInvoiceModal) {
                        com.example.ui.components.AddInvoiceDialog(
                            onConfirm = { cred, deb, credTin, debTin, days ->
                                viewModel.registerNewInvoice(cred, deb, credTin, debTin, days)
                            },
                            onDismiss = { viewModel.toggleAddInvoiceModal(false) }
                        )
                    }

                    if (showListInvoiceModal) {
                        com.example.ui.components.ListInvoiceDialog(
                            onConfirm = { cred, deb, days ->
                                viewModel.listCustomOverdueInvoice(cred, deb, days)
                            },
                            onDismiss = { viewModel.toggleListInvoiceModal(false) }
                        )
                    }

                    if (inspectingTokenDoc != null) {
                        com.example.ui.components.BackingDocInspectorDialog(
                            token = inspectingTokenDoc!!,
                            onDismiss = { viewModel.closeBackingDocModal() }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrcaatrateTopAppBar(
    selectedTab: AppTab,
    currencyIsGhs: Boolean = false,
    onToggleCurrency: () -> Unit = {},
    onTabSelect: (AppTab) -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = NavyDark),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CyanElectric.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyanElectric),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Lan,
                            contentDescription = "Orcaatrate Logo",
                            tint = CyanElectric,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ORCAATRATE",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = GoldWarm.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "GLOBAL 100K",
                                color = GoldWarm,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "Live Clearing Engine • SEC XBRL • London Set-Off",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                }
            }
        },
        actions = {
            // Interactive Currency Switcher (USD vs GH¢ at BoG 11.69 rate)
            Surface(
                color = if (currencyIsGhs) GoldWarm.copy(alpha = 0.2f) else CyanElectric.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (currencyIsGhs) GoldWarm else CyanElectric),
                modifier = Modifier
                    .clickable { onToggleCurrency() }
                    .padding(end = 8.dp)
                    .testTag("currency_toggle_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (currencyIsGhs) "GH¢ CEDIS" else "$ USD",
                        color = if (currencyIsGhs) GoldWarm else CyanElectric,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Surface(
                color = EmeraldHealthy.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(EmeraldHealthy)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "LIVE ONLY",
                        color = EmeraldHealthy,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@Composable
fun OrcaatrateBottomNav(
    selectedTab: AppTab,
    circleCount: Int,
    onTabSelect: (AppTab) -> Unit
) {
    NavigationBar(
        containerColor = NavyDark,
        tonalElevation = 8.dp,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("orcaatrate_bottom_navigation")
    ) {
        val items = listOf(
            NavigationItem(AppTab.DASHBOARD, "Overview", Icons.Default.TrendingUp),
            NavigationItem(AppTab.COMPANIES_SEC, "100K / SEC", Icons.Default.Layers),
            NavigationItem(AppTab.TOKENIZED_DEBT, "Tokenized", Icons.Default.Token),
            NavigationItem(AppTab.CIRCLE_KILLER, "Circle Killer", Icons.Default.Lan, badgeCount = circleCount),
            NavigationItem(AppTab.MARKETPLACE, "Marketplace", Icons.Default.ShoppingCart),
            NavigationItem(AppTab.SOVEREIGN_MACRO, "Sovereign", Icons.Default.AccountBalance),
            NavigationItem(AppTab.AUTO_CAMPAIGN, "Viral Ads", Icons.Default.Campaign)
        )

        items.forEach { item ->
            val isSelected = selectedTab == item.tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelect(item.tab) },
                icon = {
                    if (item.badgeCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = GoldWarm, contentColor = DeepObsidian) {
                                    Text(item.badgeCount.toString())
                                }
                            }
                        ) {
                            Icon(imageVector = item.icon, contentDescription = item.label)
                        }
                    } else {
                        Icon(imageVector = item.icon, contentDescription = item.label)
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 9.5.sp,
                        maxLines = 1,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CyanElectric,
                    selectedTextColor = CyanElectric,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = NavyCard
                ),
                modifier = Modifier.testTag("nav_${item.tab.name.lowercase()}")
            )
        }
    }
}

data class NavigationItem(
    val tab: AppTab,
    val label: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)
