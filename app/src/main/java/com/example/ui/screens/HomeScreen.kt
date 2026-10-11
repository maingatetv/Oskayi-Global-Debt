package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FilterDrama
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompanyData
import com.example.data.model.FxRates
import com.example.data.model.GhanaMacroData
import com.example.ui.AppTab
import com.example.ui.components.ComplianceDisclaimer
import com.example.ui.components.DistressedBadge
import com.example.ui.components.WorldDebtClockBanner
import com.example.ui.theme.BlueInfo
import com.example.ui.theme.CoralDistressed
import com.example.ui.theme.CyanElectric
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.EmeraldHealthy
import com.example.ui.theme.GoldLuminous
import com.example.ui.theme.GoldWarm
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavySurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextWhite
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(
    worldDebt: Double,
    companies: List<CompanyData>,
    fxRates: FxRates?,
    ghanaMacro: GhanaMacroData,
    currencyIsGhs: Boolean = false,
    onToggleCurrency: () -> Unit = {},
    onSelectCompany: (CompanyData) -> Unit,
    onNavigateTab: (AppTab) -> Unit,
    onOpenMomo: (CompanyData) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("home_screen_lazy_column"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. World Debt Clock
        item {
            WorldDebtClockBanner(
                currentDebt = worldDebt,
                currencyIsGhs = currencyIsGhs,
                onToggleCurrency = onToggleCurrency
            )
        }

        // 2. Frankfurter Live FX Bar
        item {
            FxTickerBar(fxRates = fxRates)
        }

        // 3. Three Automated Flows Action Hub
        item {
            Text(
                text = "AUTOMATED CLEARING & DEBT PIPELINES",
                color = CyanElectric,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                FlowActionCard(
                    flowLabel = "FLOW A — CIRCLE KILLER",
                    title = "Pure Multilateral Offsetting ($0 Cash Moved)",
                    description = "London clearing house principle: A->B->C->A debt loop netted to zero. Digital consent via WhatsApp / USSD. Settles only remainder $0.",
                    icon = Icons.Default.Lan,
                    accentColor = CyanElectric,
                    tag = "flow_a_button",
                    onClick = { onNavigateTab(AppTab.CIRCLE_KILLER) }
                )

                FlowActionCard(
                    flowLabel = "FLOW B — DISTRESSED MARKETPLACE",
                    title = "Overdue Invoices at Aging Discount",
                    description = "Fixed $10k invoices sold at 10%–70% OFF based on aging curve. Collectors buy pickup code via MoMo / Paystack with automated 80/20 referrer split.",
                    icon = Icons.Default.ShoppingCart,
                    accentColor = GoldWarm,
                    tag = "flow_b_button",
                    onClick = { onNavigateTab(AppTab.MARKETPLACE) }
                )

                FlowActionCard(
                    flowLabel = "FLOW C — TOP 100K REFINANCING",
                    title = "Through Net Cap Refinancing Templates",
                    description = "SEC EDGAR XBRL live debt extraction + 4 syndicate sources of funds. MTNGH GH¢85.4B facility & NVIDIA $5.73T net cap analytics.",
                    icon = Icons.Default.Layers,
                    accentColor = EmeraldHealthy,
                    tag = "flow_c_button",
                    onClick = { onNavigateTab(AppTab.COMPANIES_SEC) }
                )

                FlowActionCard(
                    flowLabel = "FLOW D — TOKENIZED DEBT (BASE L2)",
                    title = "1-for-1 Backed Instant T+0 Liquidity",
                    description = "Robinhood 190 stocks model for global debt: 10k tokens backed 1-for-1 by offshore assignment doc. USDC on Base chain 8453. Instant funds from 900k buyers.",
                    icon = Icons.Default.Token,
                    accentColor = GoldLuminous,
                    tag = "flow_d_button",
                    onClick = { onNavigateTab(AppTab.TOKENIZED_DEBT) }
                )
            }
        }

        // 4. Ghana Sovereign & Financial Macro Highlight
        item {
            GhanaMacroHighlightCard(
                macro = ghanaMacro,
                onViewFull = { onNavigateTab(AppTab.SOVEREIGN_MACRO) }
            )
        }

        // 5. Global 100K Leaderboard Snapshot
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "GLOBAL 100K NET CAP RADAR",
                        color = CyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Live Net Cap = Market Cap - Debt (SEC XBRL / GSE)",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                OutlinedButton(
                    onClick = { onNavigateTab(AppTab.COMPANIES_SEC) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanElectric)
                ) {
                    Text(text = "View All", fontSize = 11.sp)
                }
            }
        }

        items(companies) { comp ->
            CompanyLeaderboardCard(
                company = comp,
                onSelect = { onSelectCompany(comp) },
                onRefinance = { onOpenMomo(comp) }
            )
        }

        // Compliance Footer
        item {
            ComplianceDisclaimer()
        }
    }
}

@Composable
fun FxTickerBar(
    fxRates: FxRates?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, NavyBorder)))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CurrencyExchange,
                        contentDescription = null,
                        tint = CyanElectric,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "FRANKFURTER ECB LIVE FX RATES (BASE: USD)",
                        color = CyanElectric,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = fxRates?.date ?: "Live Feed",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val rates = fxRates?.rates ?: mapOf("GHS" to 11.686, "EUR" to 0.892, "ZAR" to 16.53, "NGN" to 1585.0, "INR" to 96.73)
                rates.forEach { (sym, rate) ->
                    Surface(
                        color = NavyCard,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "USD/$sym: ", color = TextSecondary, fontSize = 11.sp)
                            Text(
                                text = String.format("%.3f", rate),
                                color = GoldLuminous,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlowActionCard(
    flowLabel: String,
    title: String,
    description: String,
    icon: ImageVector,
    accentColor: androidx.compose.ui.graphics.Color,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(tag),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, accentColor.copy(alpha = 0.5f))))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = accentColor.copy(alpha = 0.15f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = flowLabel, color = accentColor, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                Text(text = title, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, color = TextSecondary, fontSize = 11.sp, lineHeight = 14.sp)
            }

            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun GhanaMacroHighlightCard(
    macro: GhanaMacroData,
    onViewFull: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onViewFull)
            .testTag("ghana_macro_highlight_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavyCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(GoldWarm.copy(alpha = 0.4f), NavyBorder)))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = GoldWarm, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "BANK OF GHANA & SOVEREIGN DOSSIER", color = GoldWarm, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = macro.period, color = TextSecondary, fontSize = 11.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Total Public Debt", color = TextSecondary, fontSize = 10.5.sp)
                    Text(text = "GH¢${macro.totalGhs}B", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = "≈ $${macro.totalUsd}B USD", color = CyanElectric, fontSize = 11.sp)
                }

                Column {
                    Text(text = "Domestic / External", color = TextSecondary, fontSize = 10.5.sp)
                    Text(text = "GH¢${macro.domesticGhs}B / $${macro.externalUsd}B", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Debt-to-GDP: ${macro.debtToGdp}%", color = EmeraldHealthy, fontSize = 11.sp)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "GFIM Liquidity", color = TextSecondary, fontSize = 10.5.sp)
                    Text(text = "GH¢${macro.gfimVolumeGhs}B", color = GoldLuminous, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "${macro.gfimTrades} trades", color = TextSecondary, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun CompanyLeaderboardCard(
    company: CompanyData,
    onSelect: () -> Unit,
    onRefinance: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .testTag("company_card_${company.ticker}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, NavyBorder)))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = NavyCard
                    ) {
                        Text(
                            text = "#${company.rank}",
                            color = TextSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = company.ticker, color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(text = company.name, color = TextSecondary, fontSize = 11.sp, maxLines = 1)
                    }
                }

                DistressedBadge(isDistressed = company.isDistressed, ratio = company.debtToCapPercent)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Market Cap", color = TextMuted, fontSize = 10.sp)
                    Text(text = formatter.format(company.marketCapUsd), color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                Column {
                    Text(text = "Debt (SEC/XBRL)", color = TextMuted, fontSize = 10.sp)
                    Text(text = formatter.format(company.debtUsd), color = CoralDistressed, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Net Capitalization", color = TextMuted, fontSize = 10.sp)
                    Text(
                        text = formatter.format(company.netCapitalization),
                        color = if (company.netCapitalization >= 0) EmeraldHealthy else CoralDistressed,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Refinance Savings: ${formatter.format(company.annualSavingsUsd)}/yr",
                    color = GoldLuminous,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = onRefinance,
                    colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("refinance_button_${company.ticker}")
                ) {
                    Text(text = "Refinancing PDF", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
