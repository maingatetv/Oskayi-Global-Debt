package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CbgProfile
import com.example.data.model.GhanaMacroData
import com.example.data.model.WorldBankIndicator
import com.example.ui.components.ComplianceDisclaimer
import com.example.ui.components.DetailRow
import com.example.ui.theme.CoralDistressed
import com.example.ui.theme.CyanElectric
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
fun SovereignMacroScreen(
    macro: GhanaMacroData,
    cbg: CbgProfile,
    worldBankList: List<WorldBankIndicator>,
    modifier: Modifier = Modifier
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("sovereign_macro_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(GoldWarm, CyanElectric)))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = GoldWarm.copy(alpha = 0.2f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = GoldWarm, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = "SOVEREIGN & CENTRAL BANK DOSSIER", color = GoldWarm, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                Text(text = "Bank of Ghana GH¢733.9B ($62.8B)", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }

                        Surface(
                            color = EmeraldHealthy.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "45.9% of GDP",
                                color = EmeraldHealthy,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Official Summary of Economic & Financial Data (BoG Sept 2026). Total public debt GH¢733.9 billion. Conversion benchmark 11.686 GHS/USD ($62.8B total).",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        color = NavyCard,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            DetailRow("Total Public Debt (GHS):", "GH¢${macro.totalGhs} Billion", highlight = TextWhite)
                            DetailRow("Total Public Debt (USD):", "$${macro.totalUsd} Billion USD", highlight = CyanElectric)
                            DetailRow("Domestic Debt:", "GH¢${macro.domesticGhs} Billion")
                            DetailRow("External Debt:", "$${macro.externalUsd}B ($28.8B = GH¢337.2B)")
                            DetailRow("Ghana GDP (2026):", "GH¢${macro.gdpGhs} Billion")
                            DetailRow("Debt-to-GDP Ratio:", "${macro.debtToGdp}%", highlight = EmeraldHealthy)
                            DetailRow("Primary Source:", macro.source)
                        }
                    }
                }
            }
        }

        // Ghana Fixed Income Market (GFIM) Liquidity
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, CyanElectric.copy(alpha = 0.4f))))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = CyanElectric, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "GHANA FIXED INCOME MARKET (GFIM)", color = CyanElectric, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Source: Norvan & HSJ", color = TextSecondary, fontSize = 10.5.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Monthly Volume", color = TextSecondary, fontSize = 10.5.sp)
                            Text(text = "GH¢${macro.gfimVolumeGhs}B", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Value: GH¢${macro.gfimValueGhs}B", color = CyanElectric, fontSize = 11.sp)
                        }

                        Column {
                            Text(text = "Trading Activity", color = TextSecondary, fontSize = 10.5.sp)
                            Text(text = "${macro.gfimTrades}", color = GoldLuminous, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Executed trades", color = TextSecondary, fontSize = 11.sp)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "T-Bill Auction", color = TextSecondary, fontSize = 10.5.sp)
                            Text(text = "GH¢${macro.tbillAcceptedGhs}B", color = EmeraldHealthy, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Bids: GH¢${macro.tbillBidsGhs}B", color = TextSecondary, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Consolidated Bank Ghana (CBG) Profile
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, GoldWarm.copy(alpha = 0.4f))))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = cbg.name, color = GoldWarm, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Unaudited Balance Sheet & SIGA Performance Metrics", color = TextSecondary, fontSize = 11.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = NavyCard,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            DetailRow("Total Liabilities:", "GH¢${cbg.totalLiabilitiesGhs}B (GH¢14,140,916k)")
                            DetailRow("Total Customer Deposits:", "GH¢${cbg.depositsGhs}B")
                            DetailRow("Borrowed Funds:", "GH¢${cbg.borrowedGhs}B (GH¢892.8M)")
                            DetailRow("Shareholder Equity:", "GH¢${cbg.equityGhs}B")
                            DetailRow("Retained Deficit:", "GH¢${cbg.retainedEarningsGhs}B", highlight = CoralDistressed)
                            DetailRow("Non-Performing Loans (NPL):", "${cbg.nplRatio}% (improved from 15.03%)", highlight = EmeraldHealthy)
                            DetailRow("Profit Before Tax (2024):", "GH¢160.1M (rebounded from -GH¢712.9M)", highlight = EmeraldHealthy)
                            DetailRow("Liquid Assets to Deposits:", "${cbg.liquidAssetsToDeposits}%")
                            DetailRow("Recapitalization Program:", "GH¢${cbg.recapPlanGhs}B Target")
                        }
                    }
                }
            }
        }

        // World Bank Live Sovereign Debt Feed
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = CyanElectric, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "WORLD BANK DT.DOD.DECT.CD (EXTERNAL DEBT)", color = CyanElectric, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = "Live API Feed", color = TextSecondary, fontSize = 10.5.sp)
            }
        }

        if (worldBankList.isEmpty()) {
            item {
                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "World Bank Sovereign External Debt Stocks (Current US$):", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• Ghana (GH): $28.8B USD (Official BoG/World Bank)\n• Nigeria (NG): $42.5B USD\n• South Africa (ZA): $76.2B USD\n• India (IN): $624.7B USD", color = TextSecondary, fontSize = 11.5.sp, lineHeight = 16.sp)
                    }
                }
            }
        } else {
            items(worldBankList.take(6)) { wb ->
                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "${wb.countryName} (${wb.countryCode})", color = TextWhite, fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Indicator: ${wb.indicatorCode} (${wb.date})", color = TextSecondary, fontSize = 10.sp)
                        }
                        Text(
                            text = if (wb.value != null) formatter.format(wb.value) else "N/A",
                            color = GoldLuminous,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        item {
            ComplianceDisclaimer(companyName = "Sovereign Governments")
        }
    }
}
