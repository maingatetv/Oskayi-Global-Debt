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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompanyData
import com.example.ui.components.ComplianceDisclaimer
import com.example.ui.components.DistressedBadge
import com.example.ui.components.SourceOfFundsSection
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
fun CompanyExplorerScreen(
    companies: List<CompanyData>,
    selectedCompany: CompanyData?,
    searchQuery: String,
    isLoadingSec: Boolean,
    lookupError: String?,
    onSearchChange: (String) -> Unit,
    onLookupTicker: (String) -> Unit,
    onSelectCompany: (CompanyData) -> Unit,
    onOpenMomoAuth: (CompanyData) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("company_explorer_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search & SEC Live Ticker Query Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(CyanElectric, NavyBorder)))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SEC EDGAR LIVE XBRL & GLOBAL 100K RADAR",
                        color = CyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Query live 10-K/10-Q liabilities directly from sec.gov with zero mock data. Computes Net Cap & refinancing discount.",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = onSearchChange,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("sec_ticker_search_input"),
                            placeholder = { Text("Ticker (e.g. NVDA, AAPL, MSFT, MTNGH)", color = TextMuted, fontSize = 13.sp) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                keyboard?.hide()
                                onLookupTicker(searchQuery)
                            }),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CyanElectric,
                                unfocusedBorderColor = NavyBorder,
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite
                            ),
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = CyanElectric)
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                keyboard?.hide()
                                onLookupTicker(searchQuery)
                            },
                            modifier = Modifier
                                .height(56.dp)
                                .testTag("sec_fetch_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = CyanElectric, contentColor = DeepObsidian),
                            shape = RoundedCornerShape(10.dp),
                            enabled = searchQuery.isNotBlank() && !isLoadingSec
                        ) {
                            if (isLoadingSec) {
                                CircularProgressIndicator(color = DeepObsidian, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            } else {
                                Text("SEC Fetch", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }

                    // Error notice fallback rule
                    if (lookupError != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = CoralDistressed.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CoralDistressed.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth().testTag("sec_error_notice")
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = CoralDistressed, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = lookupError,
                                    color = CoralDistressed,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Selected Company Deep Analysis
        if (selectedCompany != null) {
            item {
                ActiveCompanyDossierCard(
                    company = selectedCompany,
                    onOpenMomo = { onOpenMomoAuth(selectedCompany) }
                )
            }

            item {
                SourceOfFundsSection(company = selectedCompany)
            }
        }

        // Quick Ticker Pills
        item {
            Text(
                text = "VERIFIED TICKER PROFILES",
                color = CyanElectric,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                companies.forEach { comp ->
                    val isSelected = selectedCompany?.ticker == comp.ticker
                    Surface(
                        color = if (isSelected) CyanElectric else NavyCard,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .clickable { onSelectCompany(comp) }
                            .testTag("ticker_pill_${comp.ticker}")
                    ) {
                        Text(
                            text = comp.ticker,
                            color = if (isSelected) DeepObsidian else TextWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            ComplianceDisclaimer(companyName = selectedCompany?.name ?: "Target")
        }
    }
}

@Composable
fun ActiveCompanyDossierCard(
    company: CompanyData,
    onOpenMomo: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("active_company_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavySurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(GoldWarm.copy(alpha = 0.5f), CyanElectric.copy(alpha = 0.5f))))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = company.ticker, color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(color = NavyCard, shape = RoundedCornerShape(4.dp)) {
                            Text(text = "CIK: ${company.cik}", color = CyanElectric, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                    Text(text = company.name, color = TextSecondary, fontSize = 12.sp)
                }

                DistressedBadge(isDistressed = company.isDistressed, ratio = company.debtToCapPercent)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Three Pillar Metrics: Cap vs Debt vs Net Cap
            Surface(
                color = NavyCard,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Market Cap", color = TextSecondary, fontSize = 11.sp)
                        Text(text = formatter.format(company.marketCapUsd), color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Column {
                        Text(text = "Debt (Liabilities)", color = TextSecondary, fontSize = 11.sp)
                        Text(text = formatter.format(company.debtUsd), color = CoralDistressed, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Net Capitalization", color = TextSecondary, fontSize = 11.sp)
                        Text(
                            text = formatter.format(company.netCapitalization),
                            color = if (company.netCapitalization >= 0) EmeraldHealthy else CoralDistressed,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Refinancing Savings Matrix
            Surface(
                color = NavyDark,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldWarm.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "REFINANCING SAVINGS ENGINE", color = GoldWarm, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(text = "10% Platform Device Charge", color = TextSecondary, fontSize = 10.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Est. Annual Interest Saved:", color = TextSecondary, fontSize = 11.sp)
                            Text(text = formatter.format(company.annualSavingsUsd), color = EmeraldHealthy, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Orcaatrate 10% Fee:", color = TextSecondary, fontSize = 11.sp)
                            Text(text = formatter.format(company.fee10PctUsd), color = GoldLuminous, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onOpenMomo,
                        modifier = Modifier.fillMaxWidth().testTag("unlock_refinancing_template_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldWarm, contentColor = DeepObsidian),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (company.ticker == "MTNGH") "Authorize MoMo PIN (Demo GH¢2,500)" else "Unlock Refinancing Template PDF",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
