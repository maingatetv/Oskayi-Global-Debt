package com.example.ui.components

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.CompanyData
import com.example.data.model.Invoice
import com.example.data.model.RefinancingTermSheet
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
fun MomoAuthDialog(
    company: CompanyData,
    msisdn: String,
    pin: String,
    status: String?,
    onMsisdnChange: (String) -> Unit,
    onPinChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NavySurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldWarm),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("momo_auth_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
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
                                Icon(
                                    imageVector = Icons.Default.PhoneAndroid,
                                    contentDescription = null,
                                    tint = GoldWarm,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "MTN MoMo Authorization",
                                color = TextWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "X-Target-Environment: mtnghana",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Target Refinancing:", color = TextSecondary, fontSize = 12.sp)
                            Text(text = "${company.ticker} (${company.name})", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Template Fee (Demo):", color = TextSecondary, fontSize = 12.sp)
                            Text(text = "GH¢2,500.00", color = GoldLuminous, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Refinancing Scope:", color = TextSecondary, fontSize = 12.sp)
                            Text(text = "Term Sheet + Assignment PDF", color = CyanElectric, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (status == null) {
                    Text(text = "Subscriber MSISDN (233...):", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = msisdn,
                        onValueChange = onMsisdnChange,
                        modifier = Modifier.fillMaxWidth().testTag("momo_msisdn_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyanElectric,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "USSD Authorization PIN (4 digits):", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = pin,
                        onValueChange = onPinChange,
                        modifier = Modifier.fillMaxWidth().testTag("momo_pin_input"),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldWarm,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        placeholder = { Text("••••", color = TextMuted) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onSubmit,
                        modifier = Modifier.fillMaxWidth().testTag("momo_submit_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldWarm, contentColor = DeepObsidian),
                        shape = RoundedCornerShape(10.dp),
                        enabled = pin.length >= 4 && msisdn.length >= 9
                    ) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Authorize GH¢2,500 via MTN MoMo", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            when (status) {
                                "INITIATING" -> {
                                    CircularProgressIndicator(color = GoldWarm, modifier = Modifier.size(36.dp))
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text("Posting X-Reference-Id UUID v4 to MoMo Collection API...", color = TextSecondary, fontSize = 12.sp, textAlign = TextAlign.Center)
                                }
                                "USSD_PROMPT" -> {
                                    CircularProgressIndicator(color = CyanElectric, modifier = Modifier.size(36.dp))
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text("USSD prompt pushed to $msisdn. Verifying HMAC & Bearer token...", color = CyanElectric, fontSize = 12.sp, textAlign = TextAlign.Center)
                                }
                                "SUCCESS" -> {
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldHealthy, modifier = Modifier.size(40.dp))
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Payment Confirmed! Generating Signed PDF & QR...", color = EmeraldHealthy, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No stock or bond traded. You receive an analytical refinancing proposal template with 7-day validity.",
                    color = TextMuted,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun TermSheetDetailDialog(
    termSheet: RefinancingTermSheet,
    onRevokeToggle: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NavySurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, CyanElectric),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("term_sheet_detail_dialog")
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
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "REFINANCING TERM SHEET",
                                color = CyanElectric,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (termSheet.status == "ISSUED") EmeraldHealthy.copy(alpha = 0.2f) else CoralDistressed.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = termSheet.status,
                                    color = if (termSheet.status == "ISSUED") EmeraldHealthy else CoralDistressed,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = termSheet.refCode,
                            color = GoldLuminous,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        DetailRow("Issuer/Target:", "${termSheet.ticker} — ${termSheet.companyName}")
                        DetailRow("Market Cap:", formatter.format(termSheet.marketCapUsd))
                        DetailRow("Total Liabilities:", formatter.format(termSheet.debtRealUsd))
                        DetailRow("Debt-to-Cap Ratio:", "${String.format("%.2f", termSheet.debtToCapRatioPct)}%")
                        DetailRow("Net Capitalization:", formatter.format(termSheet.netCapUsd))
                        DetailRow("Benchmark Old Rate:", "${String.format("%.2f", termSheet.oldRatePct)}%")
                        DetailRow("Benchmark New Rate:", "${String.format("%.2f", termSheet.newRatePct)}%")
                        DetailRow("Estimated Annual Savings:", formatter.format(termSheet.annualSavingsUsd), highlight = EmeraldHealthy)
                        DetailRow("10% Analytical Fee:", formatter.format(termSheet.fee10PctUsd), highlight = GoldLuminous)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Verification and QR Section
                Surface(
                    color = NavyDark,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White,
                            modifier = Modifier.size(60.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.QrCode2,
                                    contentDescription = "QR Code",
                                    tint = Color.Black,
                                    modifier = Modifier.size(54.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "DIGITAL VERIFICATION HASH", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = termSheet.qrPayload, color = CyanElectric, fontSize = 11.sp, maxLines = 1)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Valid: 7 days revocable window", color = GoldWarm, fontSize = 10.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onRevokeToggle(termSheet.refCode) },
                        modifier = Modifier.weight(1f).testTag("term_sheet_revoke_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (termSheet.status == "ISSUED") CoralDistressed else EmeraldHealthy
                        )
                    ) {
                        Text(text = if (termSheet.status == "ISSUED") "Revoke Status" else "Reinstate Status", fontSize = 12.sp)
                    }

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).testTag("term_sheet_done_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian)
                    ) {
                        Text(text = "Done", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun BuyInvoiceDialog(
    invoice: Invoice,
    pickupCode: String?,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var buyerPhone = "0244008841"

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NavySurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, CyanElectric),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("buy_invoice_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PURCHASE DISTRESSED INVOICE",
                        color = CyanElectric,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        DetailRow("Invoice ID:", invoice.id)
                        DetailRow("Creditor TIN:", invoice.creditorTin)
                        DetailRow("Debtor TIN:", invoice.debtorTin)
                        DetailRow("Face Value (FIXED):", "$10,000.00")
                        DetailRow("Aging Overdue:", "${invoice.daysOverdue} days")
                        DetailRow("Aging Discount:", "${String.format("%.0f", invoice.discountPercent)}% OFF", highlight = GoldLuminous)
                        DetailRow("Sale Price (Purchase):", "$${String.format("%,.0f", invoice.salePriceUsd)}", highlight = EmeraldHealthy)
                        DetailRow("Collector Upside:", "$${String.format("%,.0f", 10000.0 - invoice.salePriceUsd)}", highlight = CyanElectric)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (pickupCode == null) {
                    Text(
                        text = "Paystack / MoMo Split Automation:",
                        color = TextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• 80% to Platform clearing reserve ($${String.format("%,.0f", invoice.salePriceUsd * 0.80)})\n• 20% to Referrer subaccount auto-transfer ($${String.format("%,.0f", invoice.salePriceUsd * 0.20)})\n• Collector receives Notice of Assignment to legally enforce full $10,000 against debtor.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onConfirm(buyerPhone) },
                        modifier = Modifier.fillMaxWidth().testTag("confirm_buy_invoice_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Pay $${String.format("%,.0f", invoice.salePriceUsd)} & Issue Notice of Assignment", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                } else {
                    Surface(
                        color = EmeraldHealthy.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldHealthy),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldHealthy, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "NOTICE OF ASSIGNMENT ISSUED", color = EmeraldHealthy, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Pickup Code:", color = TextSecondary, fontSize = 11.sp)
                            Text(text = pickupCode, color = GoldLuminous, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Legal set-off assignment documentation prepared. Debtor notified per statutory assignment rules.",
                                color = TextWhite,
                                fontSize = 10.5.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth().testTag("close_buy_invoice_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyCard, contentColor = TextWhite),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    highlight: Color? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = TextSecondary, fontSize = 11.5.sp)
        Text(
            text = value,
            color = highlight ?: TextWhite,
            fontWeight = if (highlight != null) FontWeight.Bold else FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun TokenBuyDialog(
    token: com.example.data.model.TokenizedDebt,
    amountStr: String,
    walletStr: String,
    paymentMethod: String,
    txSuccess: String?,
    onAmountChange: (String) -> Unit,
    onWalletChange: (String) -> Unit,
    onPaymentMethodChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }
    val count = amountStr.toLongOrNull() ?: 1L
    val totalCost = count * token.tokenPriceUsd
    val faceValue = count * token.faceValueUsd

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NavySurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, CyanElectric),
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .testTag("token_buy_dialog")
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
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "FLOW D: TOKENIZED DEBT (BASE L2)",
                                color = CyanElectric,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = EmeraldHealthy.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "1-FOR-1 BACKED",
                                    color = EmeraldHealthy,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "${token.tokenName} (${token.tokenSymbol})",
                            color = GoldLuminous,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(30.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        DetailRow("Base L2 Contract:", "${token.contractAddress.take(8)}...${token.contractAddress.takeLast(6)}")
                        DetailRow("USDC Contract (Base):", "0x83358...A02913")
                        DetailRow("Price per Token:", "$${String.format("%,.0f", token.tokenPriceUsd)} (${String.format("%.0f", token.discountPercent)}% OFF)", highlight = GoldLuminous)
                        DetailRow("Face Value per Token:", "$${String.format("%,.0f", token.faceValueUsd)}")
                        DetailRow("Available Supply:", "${String.format("%,d", token.availableTokens)} / ${String.format("%,d", token.totalSupply)}")
                        DetailRow("Backing Document:", "Offshore Assignment Doc (SHA-256)", highlight = CyanElectric)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (txSuccess == null) {
                    Text(text = "Tokens to Buy:", color = TextSecondary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    OutlinedTextField(
                        value = amountStr,
                        onValueChange = onAmountChange,
                        modifier = Modifier.fillMaxWidth().testTag("token_buy_amount_input"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyanElectric,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Buyer Wallet (Base 0x...) / Phone:", color = TextSecondary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    OutlinedTextField(
                        value = walletStr,
                        onValueChange = onWalletChange,
                        modifier = Modifier.fillMaxWidth().testTag("token_buy_wallet_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldWarm,
                            unfocusedBorderColor = NavyBorder,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = NavyDark,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Total Investment:", color = TextSecondary, fontSize = 10.5.sp)
                                Text(text = formatter.format(totalCost), color = CyanElectric, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Face Value Claim:", color = TextSecondary, fontSize = 10.5.sp)
                                Text(text = formatter.format(faceValue), color = EmeraldHealthy, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.fillMaxWidth().testTag("confirm_token_buy_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Mint & Buy ${formatter.format(totalCost)} (Instant T+0)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                } else {
                    Surface(
                        color = EmeraldHealthy.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldHealthy),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldHealthy, modifier = Modifier.size(34.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "BASE L2 TRANSACTION CONFIRMED", color = EmeraldHealthy, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Transaction Hash (Basescan):", color = TextSecondary, fontSize = 10.sp)
                            Text(text = txSuccess, color = GoldLuminous, fontFamily = FontFamily.Monospace, fontSize = 10.sp, maxLines = 1)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "1-for-1 Backing SHA-256 hash verified. Collection rights transferred to buyer wallet. Trades 24/7 on Base.",
                                color = TextWhite,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyCard, contentColor = TextWhite),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Close")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Backed 1-for-1 by legal assignment doc offshore (Robinhood 190 stocks model). Buyer holds collection rights claim under statutory assignment.",
                    color = TextMuted,
                    fontSize = 9.5.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AddInvoiceDialog(
    onConfirm: (String, String, String, String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var creditor by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("Accra Global Logistics") }
    var debtor by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("Volta Mineral Works") }
    var creditorTin by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("GH-AGL-8821") }
    var debtorTin by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("GH-VMW-3419") }
    var daysOverdue by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("30") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NavySurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, CyanElectric),
            modifier = Modifier.fillMaxWidth().padding(6.dp).testTag("add_invoice_dialog")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "REGISTER INVOICE (FIXED $10K)",
                        color = CyanElectric,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = creditor,
                    onValueChange = { creditor = it },
                    label = { Text("Creditor (Company Name)", color = TextSecondary, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth().testTag("add_invoice_creditor"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = creditorTin,
                    onValueChange = { creditorTin = it },
                    label = { Text("Creditor TIN / Ghana Card", color = TextSecondary, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth().testTag("add_invoice_creditor_tin"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = debtor,
                    onValueChange = { debtor = it },
                    label = { Text("Debtor (Company Name)", color = TextSecondary, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth().testTag("add_invoice_debtor"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = debtorTin,
                    onValueChange = { debtorTin = it },
                    label = { Text("Debtor TIN / Ghana Card", color = TextSecondary, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth().testTag("add_invoice_debtor_tin"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        onConfirm(creditor, debtor, creditorTin, debtorTin, daysOverdue.toIntOrNull() ?: 15)
                    },
                    modifier = Modifier.fillMaxWidth().testTag("submit_add_invoice_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Register $10,000 Invoice for Clearing Loop", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun ListInvoiceDialog(
    onConfirm: (String, String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var creditor by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("Kumasi Industrial Supplies") }
    var debtor by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("Oti River Power") }
    var selectedDays by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(90) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NavySurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldWarm),
            modifier = Modifier.fillMaxWidth().padding(6.dp).testTag("list_invoice_dialog")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LIST OVERDUE INVOICE ON MARKETPLACE",
                        color = GoldWarm,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = creditor,
                    onValueChange = { creditor = it },
                    label = { Text("Creditor Name", color = TextSecondary, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = debtor,
                    onValueChange = { debtor = it },
                    label = { Text("Debtor Name", color = TextSecondary, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Aging Schedule & Discount:", color = TextSecondary, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(30 to "10%", 60 to "20%", 90 to "40%", 180 to "70%").forEach { (d, pct) ->
                        val isSel = selectedDays == d
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSel) GoldWarm else NavyCard,
                            modifier = Modifier.weight(1f).clickable { selectedDays = d }
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${d}d", color = if (isSel) DeepObsidian else TextWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(text = pct, color = if (isSel) DeepObsidian else GoldLuminous, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { onConfirm(creditor, debtor, selectedDays) },
                    modifier = Modifier.fillMaxWidth().testTag("submit_list_invoice_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldWarm, contentColor = DeepObsidian),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    val discount = when(selectedDays) { 180 -> 70; 90 -> 40; 60 -> 20; else -> 10 }
                    val price = 10000.0 * (1.0 - discount / 100.0)
                    Text("List for $${String.format("%,.0f", price)} ($discount% OFF)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BackingDocInspectorDialog(
    token: com.example.data.model.TokenizedDebt,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = NavySurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldHealthy),
            modifier = Modifier.fillMaxWidth().padding(6.dp).testTag("backing_doc_dialog")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = EmeraldHealthy, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "1-FOR-1 BACKING DOCUMENT AUDIT", color = EmeraldHealthy, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = NavyCard,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        DetailRow("Token Reference:", token.refCode)
                        DetailRow("Issuer/Target:", "${token.ticker} (${token.tokenName})")
                        DetailRow("Total Tranche:", "$10,000,000.00 USD (${token.totalSupply} tokens)")
                        DetailRow("Token Price / Face:", "$${String.format("%,.0f", token.tokenPriceUsd)} / $1,000.00")
                        DetailRow("Base Blockchain:", "Chain ID 8453 (Base Mainnet)")
                        DetailRow("USDC Contract:", "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = NavyDark,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "CRYPTOGRAPHIC HASH (SHA-256):", color = GoldLuminous, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = token.backingDocHash, color = TextWhite, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "OFFSHORE STORAGE REPOSITORY:", color = CyanElectric, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${token.backingDocUrl}${token.refCode}.pdf", color = TextSecondary, fontSize = 10.5.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Statutory Assignment Clause: Originating creditor assigns all collection rights in the underlying $10M facility to token holders. Backed 1-for-1 exactly like Robinhood 190 tokenized stocks.",
                    color = TextMuted,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldHealthy, contentColor = DeepObsidian),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Close Document Audit", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

