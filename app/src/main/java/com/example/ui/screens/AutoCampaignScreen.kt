package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CompanyData
import com.example.ui.components.ComplianceDisclaimer
import com.example.ui.components.DetailRow
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

@Composable
fun AutoCampaignScreen(
    selectedCompany: CompanyData?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val targetTicker = selectedCompany?.ticker ?: "NVDA"
    val targetCap = if (selectedCompany != null) "$${String.format("%.2f", selectedCompany.marketCapUsd / 1e12)}T" else "$5.73T"
    val targetDebt = if (selectedCompany != null) "$${String.format("%.1f", selectedCompany.debtUsd / 1e9)}B" else "$11B"
    val targetRatio = if (selectedCompany != null) "${String.format("%.2f", selectedCompany.debtToCapPercent)}%" else "0.19%"

    fun copyToClipboard(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("auto_campaign_screen"),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
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
                                    Icon(imageVector = Icons.Default.Campaign, contentDescription = null, tint = CyanElectric, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = "AUTO-ADVERTISING ENGINE", color = CyanElectric, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                Text(text = "Zero Manual Distribution (3-Way Viral Loop)", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Automated distribution loops driving traffic: 100K indexed Google SEO pages, embeddable financial widget with organic backlinking, and automated algorithmic social syndication.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // 1. Google Indexing API Automation
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, CyanElectric.copy(alpha = 0.4f))))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "1. 100K PAGES GOOGLE INDEXING API", color = CyanElectric, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Submits 100 URLs/hour to Google Indexing API (URL_UPDATED). Titles update every 6 hours with real debt & cap.",
                        color = TextSecondary,
                        fontSize = 11.5.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = NavyCard,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            DetailRow("Queue Velocity:", "100 URLs / hour (Automated Cron)")
                            DetailRow("Addressable Pages:", "100,000 Companies & Sovereign Dossiers")
                            DetailRow("Est. Organic Footprint:", "1,000,000 daily organic search hits", highlight = EmeraldHealthy)
                            DetailRow("Current Payload URL:", "https://orcaatrate.com/c/$targetTicker-$targetDebt-debt-$targetCap-cap")
                        }
                    }
                }
            }
        }

        // 2. Embeddable Widget Viral Backlink Loop
        item {
            val widgetCode = "<script src=\"https://orcaatrate.com/widget.js?company=$targetTicker&theme=dark\"></script>"
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, GoldWarm.copy(alpha = 0.4f))))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "2. VIRAL EMBEDDABLE WIDGET LOOP", color = GoldWarm, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { copyToClipboard(widgetCode, "Widget Script") }, modifier = Modifier.size(28.dp)) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = GoldWarm)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Embed code for financial blogs & blogs. Renders live interactive badges and automatically returns high-DA backlinks to /c/$targetTicker.",
                        color = TextSecondary,
                        fontSize = 11.5.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = NavyDark,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = widgetCode,
                            color = CyanElectric,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Live Widget Preview
                    Surface(
                        color = NavyCard,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CyanElectric.copy(alpha = 0.3f)),
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
                                Text(text = "$targetTicker $targetCap live + Debt $targetDebt (Ratio $targetRatio)", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Powered by Orcaatrate Clearing Engine", color = GoldWarm, fontSize = 10.sp)
                            }
                            Surface(color = CyanElectric.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp)) {
                                Text(text = "LIVE BADGE", color = CyanElectric, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                            }
                        }
                    }
                }
            }
        }

        // 3. Algorithmic Content Repurposer (Social Posts)
        item {
            val tweetText = "$targetTicker cap now $targetCap, debt ratio $targetRatio — 5% OFF refinancing window — https://orcaatrate.com/c/$targetTicker?utm_source=auto&utm_medium=twitter #stocks #macro"
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NavyBorder, EmeraldHealthy.copy(alpha = 0.4f))))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "3. SOCIAL SYNDICATION GENERATOR", color = EmeraldHealthy, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { copyToClipboard(tweetText, "Social Post") }, modifier = Modifier.size(28.dp)) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = EmeraldHealthy)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Triggers automatically when 6h market cap changes >2%. Dispatched via Buffer API with UTM tracking.",
                        color = TextSecondary,
                        fontSize = 11.5.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = NavyDark,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "TWITTER / X TEMPLATE:", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tweetText,
                                color = TextWhite,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { copyToClipboard(tweetText, "Social Post") },
                        modifier = Modifier.fillMaxWidth().testTag("copy_social_post_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldHealthy, contentColor = DeepObsidian),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Copy Automated Post Template", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        item {
            ComplianceDisclaimer()
        }
    }
}
