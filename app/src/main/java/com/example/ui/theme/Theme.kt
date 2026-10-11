package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CyanElectric,
    onPrimary = DeepObsidian,
    primaryContainer = NavyCard,
    onPrimaryContainer = CyanGlow,
    secondary = GoldLuminous,
    onSecondary = DeepObsidian,
    secondaryContainer = NavySurface,
    onSecondaryContainer = GoldLuminous,
    tertiary = EmeraldHealthy,
    onTertiary = DeepObsidian,
    background = DeepObsidian,
    onBackground = TextWhite,
    surface = NavyDark,
    onSurface = TextWhite,
    surfaceVariant = NavySurface,
    onSurfaceVariant = TextSecondary,
    outline = NavyBorder,
    error = CoralDistressed,
    onError = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF007A99),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6F5FB),
    onPrimaryContainer = Color(0xFF004D60),
    secondary = Color(0xFFB37400),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF1D6),
    onSecondaryContainer = Color(0xFF6B4500),
    tertiary = Color(0xFF048A64),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightCard,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
    error = Color(0xFFD32F2F),
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our signature Orcaatrate palette for distinct financial identity
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme // Orcaatrate dark financial terminal style works best in high-contrast dark
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
