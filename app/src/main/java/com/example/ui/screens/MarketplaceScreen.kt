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
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.Invoice
import com.example.data.model.InvoiceStatus
import com.example.ui.components.ComplianceDisclaimer
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
fun MarketplaceScreen(
    invoices: List<Invoice>,
    onBuyInvoice: (Invoice) -> Unit,
    onOpenListInvoice: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("marketplace_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(GoldWarm, NavyBorder)))
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
                                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, tint = GoldWarm, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = "FLOW B — DISTRESSED MARKETPLACE", color = GoldWarm, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                Text(text = "Legal Debt Assignment & Pickup Codes", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Surface(
                            color = GoldWarm.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "FIXED $10k Face",
                                color = GoldLuminous,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Companies list overdue invoices at algorithmic discounts. Debt collectors buy pickup codes with statutory Notice of Assignment (NOA). 80/20 platform/referrer split automated via Paystack & MoMo.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Aging Discount Schedule Matrix
                    Surface(
                        color = NavyCard,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "STATUTORY AGING DISCOUNT CURVE:", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                DiscountPill(days = "30 Days", off = "10% OFF", price = "$9,000")
                                DiscountPill(days = "60 Days", off = "20% OFF", price = "$8,000")
                                DiscountPill(days = "90 Days", off = "40% OFF", price = "$6,000")
                                DiscountPill(days = ">180 Days", off = "70% OFF", price = "$3,000")
                            }
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AVAILABLE OVERDUE INVOICES FOR ASSIGNMENT",
                        color = CyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${invoices.size} Listed Overdue Notes",
                        color = TextSecondary,
                        fontSize = 10.5.sp
                    )
                }

                Button(
                    onClick = onOpenListInvoice,
                    colors = ButtonDefaults.buttonColors(containerColor = NavyCard, contentColor = GoldWarm),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("open_list_invoice_button")
                ) {
                    Text("+ List Overdue Note", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        items(invoices) { inv ->
            DistressedInvoiceCard(
                invoice = inv,
                onBuy = { onBuyInvoice(inv) }
            )
        }

        item {
            ComplianceDisclaimer(companyName = "Creditors & Assignees")
        }
    }
}

@Composable
fun DiscountPill(days: String, off: String, price: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = days, color = TextSecondary, fontSize = 10.sp)
        Text(text = off, color = GoldLuminous, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
        Text(text = price, color = TextWhite, fontSize = 10.sp)
    }
}

@Composable
fun DistressedInvoiceCard(
    invoice: Invoice,
    onBuy: () -> Unit
) {
    val isSold = invoice.status == InvoiceStatus.SOLD
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("invoice_card_${invoice.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, NavyBorder)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = invoice.id, color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = if (isSold) EmeraldHealthy.copy(alpha = 0.2f) else CoralDistressed.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (isSold) "ASSIGNED (SOLD)" else "${invoice.daysOverdue} DAYS OVERDUE",
                                color = if (isSold) EmeraldHealthy else CoralDistressed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(text = "${invoice.creditorName} ➔ ${invoice.debtorName}", color = TextSecondary, fontSize = 11.5.sp)
                }

                Surface(
                    color = GoldWarm.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${String.format("%.0f", invoice.discountPercent)}% OFF",
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
                        Text(text = "Face Value", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = "$10,000.00", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    Column {
                        Text(text = "Purchase Price", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = formatter.format(invoice.salePriceUsd), color = CyanElectric, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Collector Margin", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = formatter.format(10000.0 - invoice.salePriceUsd), color = EmeraldHealthy, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!isSold) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Paystack / MoMo Split: 80% Platform, 20% Referrer",
                        color = TextMuted,
                        fontSize = 10.sp
                    )

                    Button(
                        onClick = onBuy,
                        modifier = Modifier.testTag("buy_code_button_${invoice.id}"),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldWarm, contentColor = DeepObsidian),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(text = "Buy Pickup Code", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Surface(
                    color = EmeraldHealthy.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.AssignmentTurnedIn, contentDescription = null, tint = EmeraldHealthy, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Assigned under Pickup Code: ${invoice.pickupCode}. Notice of Assignment delivered.",
                            color = EmeraldHealthy,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
