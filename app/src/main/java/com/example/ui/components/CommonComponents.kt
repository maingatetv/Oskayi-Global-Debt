package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompanyData
import com.example.data.model.RefinancingTermSheet
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.BlueInfo
import com.example.ui.theme.CoralDistressed
import com.example.ui.theme.CyanElectric
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.EmeraldHealthy
import com.example.ui.theme.GoldLuminous
import com.example.ui.theme.GoldWarm
import com.example.ui.theme.NavyBorder
import com.example.ui.theme.NavyCard
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavySurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextWhite
import java.text.NumberFormat
import java.util.Locale

@Composable
fun WorldDebtClockBanner(
    currentDebt: Double,
    currencyIsGhs: Boolean = false,
    onToggleCurrency: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }
    val formattedDebt = if (currencyIsGhs) {
        "GH¢" + String.format(Locale.US, "%,.0f", currentDebt * 11.686)
    } else {
        formatter.format(currentDebt)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggleCurrency() }
            .testTag("world_debt_clock_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(CyanElectric, GoldLuminous)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(CoralDistressed.copy(alpha = alpha))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "GLOBAL DEBT CLOCK (LIVE TICKER)",
                        color = GoldLuminous,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Surface(
                    color = CoralDistressed.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CoralDistressed, GoldWarm)))
                ) {
                    Text(
                        text = if (currencyIsGhs) "+GH¢736,218 / sec" else "+$63,000 / sec",
                        color = GoldLuminous,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formattedDebt,
                color = TextWhite,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (currencyIsGhs) "BoG parity 11.686 GHS/USD. London clearing set-off principle." else "Baseline $365.5T global debt. London clearing set-off principle.",
                    color = TextSecondary,
                    fontSize = 11.5.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (currencyIsGhs) "VIEW IN USD >" else "VIEW IN GHS >",
                    color = CyanElectric,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DistressedBadge(
    isDistressed: Boolean,
    ratio: Double,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isDistressed) CoralDistressed.copy(alpha = 0.2f) else EmeraldHealthy.copy(alpha = 0.2f)
    val textColor = if (isDistressed) CoralDistressed else EmeraldHealthy
    val label = if (isDistressed) "DISTRESSED (${String.format("%.1f", ratio)}%)" else "HEALTHY (${String.format("%.1f", ratio)}%)"

    Surface(
        modifier = modifier,
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(textColor, textColor.copy(alpha = 0.5f))))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isDistressed) Icons.Default.Warning else Icons.Default.CheckCircle,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                color = textColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SourceOfFundsSection(
    company: CompanyData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, CyanElectric.copy(alpha = 0.4f))))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "SYNDICATED SOURCES OF FUNDS",
                color = CyanElectric,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Refinancing liquidity pipeline backing template redemption:",
                color = TextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            FundSourceItem(
                title = "Source 1: 9 Local Banks Syndicate",
                detail = "Ecobank, GCB, StanChart, Absa, Stanbic, SG, Zenith, GT, Fidelity — GH¢77.7B balances at BoG, GH¢362.2B deposits (35% oversubscribed).",
                badge = "GH¢362.2B Pool"
            )

            Spacer(modifier = Modifier.height(10.dp))

            FundSourceItem(
                title = "Source 2: Ghana Fixed Income Market (GFIM)",
                detail = "Volume GH¢49.0B (Aug/Sept 2026), value GH¢43.2B, 36,124 trades. T-bill auction GH¢8.2B bids, GH¢7.21B accepted. T+1 instant settlement.",
                badge = "GH¢49.0B Monthly"
            )

            Spacer(modifier = Modifier.height(10.dp))

            FundSourceItem(
                title = "Source 3: Operating Cash Flow & Float",
                detail = if (company.ticker == "MTNGH") {
                    "Scancom PLC EBITDA GH¢14.7B (60.1% margin) + MoMo Float GH¢37.14B (GH¢13.4B bank-owned + GH¢13.9B wallets)."
                } else {
                    "${company.name} operating cash flows and liquid reserves offset against fixed debt schedules."
                },
                badge = "Internal Cash"
            )

            Spacer(modifier = Modifier.height(10.dp))

            FundSourceItem(
                title = "Source 4: Group & Cross-Border Facilities",
                detail = "Intercompany facilities benchmarked at T-bill + 5% spread (18.8% vs old 34.75%) with Frankfurter ECB FX conversion.",
                badge = "18.8% Benchmark"
            )
        }
    }
}

@Composable
fun FundSourceItem(
    title: String,
    detail: String,
    badge: String
) {
    Surface(
        color = NavyCard,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Surface(
                    color = CyanElectric.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = badge,
                        color = CyanElectric,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = detail,
                color = TextSecondary,
                fontSize = 11.5.sp,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
fun ComplianceDisclaimer(
    companyName: String = "Entities",
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDark.copy(alpha = 0.7f)),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(NavyBorder, NavyBorder)))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = GoldWarm,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "REGULATORY & FINDER EXEMPTION NOTICE",
                    color = GoldWarm,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Not affiliated with $companyName. Info only. Data Source: SEC EDGAR public filings + CompaniesMarketCap public pages + World Bank DT.DOD.DECT.CD + Frankfurter API + GSE-API + Bank of Ghana + Base blockchain USDC 0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913. Not broker-dealer under Section 15. Token backed 1-for-1 by assignment doc offshore — you get collection rights — you don't own invoice — creditor does until assignment — info only — Template PDF + Token sale. Same as Cede & Co since 1969 and Robinhood 190 stocks tokenized.",
                color = TextMuted,
                fontSize = 10.5.sp,
                lineHeight = 14.sp
            )
        }
    }
}
