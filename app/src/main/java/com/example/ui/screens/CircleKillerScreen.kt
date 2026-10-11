package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Invoice
import com.example.data.model.InvoiceStatus
import com.example.data.model.NettingCircle
import com.example.ui.components.ComplianceDisclaimer
import com.example.ui.theme.BlueInfo
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
fun CircleKillerScreen(
    invoices: List<Invoice>,
    circles: List<NettingCircle>,
    onRunDetection: () -> Unit,
    onExecuteSetOff: (String) -> Unit,
    onOpenAddInvoice: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("circle_killer_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Clearing House Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(CyanElectric, NavyBorder)))
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
                                    Icon(imageVector = Icons.Default.Lan, contentDescription = null, tint = CyanElectric, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = "FLOW A — CIRCLE KILLER", color = CyanElectric, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                Text(text = "London Clearing House Principle", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Surface(
                            color = EmeraldHealthy.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "$0 Cash Moved",
                                color = EmeraldHealthy,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Automated graph cycle search identifies circular obligations (A owes B, B owes C, C owes A). Digital consent executes legal set-off settling obligations to exactly $0 remainder.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Scan Interval:", color = TextSecondary, fontSize = 10.5.sp)
                            Text(text = "Every 6h weekdays / 12h weekends", color = TextWhite, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = onRunDetection,
                            colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("run_detection_button")
                        ) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Detect Circles Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Active Circles Section
        item {
            Text(
                text = "DETECTED CLEARING CIRCLES",
                color = CyanElectric,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        if (circles.isEmpty()) {
            item {
                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Loop, contentDescription = null, tint = TextMuted, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "No Active Circles Detected", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap 'Detect Circles Now' to run Tarjan cycle detection across registered active invoices.",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(circles) { circle ->
                CircleCard(
                    circle = circle,
                    onExecuteSetOff = { onExecuteSetOff(circle.id) }
                )
            }
        }

        // Active Invoices Queue
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "REGISTERED INVOICES ($10,000 FIXED)",
                        color = CyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${invoices.size} Active Clearing Nodes",
                        color = TextSecondary,
                        fontSize = 10.5.sp
                    )
                }

                Button(
                    onClick = onOpenAddInvoice,
                    colors = ButtonDefaults.buttonColors(containerColor = NavyCard, contentColor = CyanElectric),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("open_add_invoice_button")
                ) {
                    Text("+ Register Invoice", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        items(invoices) { inv ->
            InvoiceNodeCard(invoice = inv)
        }

        item {
            ComplianceDisclaimer(companyName = "Clearing Participants")
        }
    }
}

@Composable
fun CircleCard(
    circle: NettingCircle,
    onExecuteSetOff: () -> Unit
) {
    val isNetted = circle.status == "NETTED_TO_ZERO"
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("circle_card_${circle.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.horizontalGradient(listOf(if (isNetted) EmeraldHealthy else GoldWarm, NavyBorder))
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "CLEARING LOOP: ${circle.id}", color = if (isNetted) EmeraldHealthy else GoldWarm, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Total Obligation: ${formatter.format(circle.totalVolumeUsd)}", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                }

                Surface(
                    color = if (isNetted) EmeraldHealthy.copy(alpha = 0.2f) else GoldWarm.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = if (isNetted) "NETTED TO $0" else "READY TO NET",
                        color = if (isNetted) EmeraldHealthy else GoldWarm,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Loop Graph Visualization (A -> B -> C -> A)
            Surface(
                color = NavyCard,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "CYCLE PATH (ZERO CASH REMITTANCE):", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    circle.invoices.forEachIndexed { i, inv ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "${inv.creditorName} (${inv.creditorTin})", color = TextWhite, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = CyanElectric, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "${inv.debtorName} (${inv.debtorTin})", color = GoldLuminous, fontSize = 11.5.sp)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "$10k", color = TextSecondary, fontSize = 11.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fee Split Breakdown (2% = $200 per company = $600 total, split 80% you 20% referrer)
            Surface(
                color = NavyDark,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "2% Clearing Fee:", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = "$${String.format("%,.0f", circle.feeAmountUsd)} ($200/co)", color = GoldLuminous, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Column {
                        Text(text = "Platform (80%):", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = "$${String.format("%,.0f", circle.platformShareUsd)}", color = CyanElectric, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Referrer (20%):", color = TextSecondary, fontSize = 10.5.sp)
                        Text(text = "$${String.format("%,.0f", circle.referrerShareUsd)}", color = EmeraldHealthy, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!isNetted) {
                Button(
                    onClick = onExecuteSetOff,
                    modifier = Modifier.fillMaxWidth().testTag("execute_set_off_${circle.id}"),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldHealthy, contentColor = DeepObsidian),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Record Consent & Net Circle to $0", fontWeight = FontWeight.Bold, fontSize = 12.sp)
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
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldHealthy, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Netting Agreement Executed. Debts offset per clearing house principle. Remaining balance: $0.00.",
                            color = EmeraldHealthy,
                            fontSize = 11.5.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InvoiceNodeCard(invoice: Invoice) {
    val isNetted = invoice.status == InvoiceStatus.NETTED

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, NavyBorder)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = invoice.id, color = CyanElectric, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = if (isNetted) EmeraldHealthy.copy(alpha = 0.2f) else NavyCard,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = invoice.status.name,
                            color = if (isNetted) EmeraldHealthy else TextSecondary,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "${invoice.creditorName} ➔ ${invoice.debtorName}", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "TIN: ${invoice.creditorTin} -> ${invoice.debtorTin}", color = TextSecondary, fontSize = 10.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = "$10,000.00", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = "${invoice.daysOverdue}d overdue", color = GoldWarm, fontSize = 10.5.sp)
            }
        }
    }
}
