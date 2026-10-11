package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BaseL2Status
import com.example.data.model.TokenizedDebt
import com.example.ui.components.ComplianceDisclaimer
import com.example.ui.components.DetailRow
import com.example.ui.theme.CoralDistressed
import com.example.ui.theme.CyanElectric
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
fun TokenizedDebtScreen(
    tokenizedDebts: List<TokenizedDebt>,
    baseStatus: BaseL2Status,
    onBuyToken: (TokenizedDebt) -> Unit,
    onRefreshRpc: () -> Unit,
    onInspectDoc: (TokenizedDebt) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("tokenized_debt_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header: Robinhood 1-for-1 Backing Model
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(CyanElectric, GoldWarm)))
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
                                color = CyanElectric.copy(alpha = 0.2f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.Token, contentDescription = null, tint = CyanElectric, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = "FLOW D — TOKENIZED DEBT (BASE L2)", color = CyanElectric, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                Text(text = "Instant T+0 Refinancing Liquidity", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Surface(
                            color = EmeraldHealthy.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "1-for-1 Real Doc",
                                color = EmeraldHealthy,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Same model as Robinhood's 190 tokenized US stocks (900k international buyers): tokens are backed 1-for-1 by real assignment contracts stored offshore. Traded 24/7 on Base blockchain. Immediate T+0 cash without bank underwriting delays.",
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
                            Text(text = "THE LEGAL CLAIM STRUCTURE (CEDE & CO PRINCIPLE):", color = GoldWarm, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Since 1969 DTCC paperwork crisis, Cede & Co holds almost every US share. Investors hold claims. A debt token is that exact legal claim at the speed of email vs a letter. You receive enforceable collection rights.",
                                color = TextWhite,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Live Base L2 Chain Telemetry Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavyCard),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, CyanElectric.copy(alpha = 0.4f))))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldHealthy)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "BASE MAINNET L2 (CHAIN ID 8453)", color = CyanElectric, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        IconButton(onClick = onRefreshRpc, modifier = Modifier.size(24.dp)) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh", tint = CyanElectric, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Block Height", color = TextSecondary, fontSize = 10.5.sp)
                            Text(text = "#${String.format("%,d", baseStatus.blockNumberDecimal)}", color = TextWhite, fontFamily = FontFamily.Monospace, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text(text = "USDC Contract", color = TextSecondary, fontSize = 10.5.sp)
                            Text(text = "0x83358...A02913", color = GoldLuminous, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Settlement Time", color = TextSecondary, fontSize = 10.5.sp)
                            Text(text = "2.0s (Instant T+0)", color = EmeraldHealthy, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Available Tokenized Debt Pools
        item {
            Text(
                text = "1-FOR-1 BACKED TOKENIZED DEBT TRANCHES",
                color = CyanElectric,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        items(tokenizedDebts) { token ->
            TokenizedDebtCard(
                token = token,
                onBuy = { onBuyToken(token) },
                onInspectDoc = { onInspectDoc(token) }
            )
        }

        item {
            ComplianceDisclaimer(companyName = "Issuing Debtors & Token Holders")
        }
    }
}

@Composable
fun TokenizedDebtCard(
    token: TokenizedDebt,
    onBuy: () -> Unit,
    onInspectDoc: () -> Unit = {}
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }
    val totalFace = token.totalSupply * token.faceValueUsd
    val totalSale = token.totalSupply * token.tokenPriceUsd

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("token_card_${token.tokenSymbol}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, GoldWarm.copy(alpha = 0.5f))))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = token.tokenSymbol, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(color = NavyCard, shape = RoundedCornerShape(4.dp)) {
                            Text(text = token.refCode, color = CyanElectric, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                    Text(text = token.tokenName, color = TextSecondary, fontSize = 11.5.sp)
                }

                Surface(
                    color = GoldWarm.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${String.format("%.0f", token.discountPercent)}% OFF",
                        color = GoldLuminous,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = NavyCard,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Token Price", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = formatter.format(token.tokenPriceUsd), color = CyanElectric, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                        Text(text = "Face: ${formatter.format(token.faceValueUsd)}", color = TextMuted, fontSize = 10.sp)
                    }

                    Column {
                        Text(text = "Total Tranche Face", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = formatter.format(totalFace), color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${String.format("%,d", token.totalSupply)} tokens", color = TextMuted, fontSize = 10.sp)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Available Supply", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = "${String.format("%,d", token.availableTokens)}", color = EmeraldHealthy, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                        Text(text = "Instant T+0", color = GoldWarm, fontSize = 10.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // SHA-256 Backing Verification - Clickable Audit Trigger
            Surface(
                color = NavyDark,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldHealthy.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onInspectDoc() }
                    .testTag("inspect_backing_doc_${token.tokenSymbol}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = EmeraldHealthy, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(text = "1-FOR-1 BACKING DOC VERIFIED", color = EmeraldHealthy, fontSize = 9.5.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = token.backingDocHash.take(28) + "...",
                                color = TextSecondary,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                maxLines = 1
                            )
                        }
                    }
                    Text(text = "AUDIT >", color = CyanElectric, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment: USDC Base / MoMo GHS",
                    color = TextMuted,
                    fontSize = 10.5.sp
                )

                Button(
                    onClick = onBuy,
                    modifier = Modifier.testTag("buy_token_button_${token.tokenSymbol}"),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Buy Tokens", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
