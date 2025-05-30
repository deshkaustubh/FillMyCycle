package com.example.fillmycycle.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// --- Girl Theme Colors ---
private val PinkPrimary = Color(0xFFFFC1E3)
private val PinkSecondary = Color(0xFFFF8DCB)
private val PinkBackground = Color(0xFFFFF0F6)
private val PinkOnPrimary = Color(0xFF3E0A26)

// --- Boy Theme Colors ---
private val BluePrimary = Color(0xFFB3E5FC)
private val BlueSecondary = Color(0xFF03A9F4)
private val BlueBackground = Color(0xFFE1F5FE)
private val BlueOnPrimary = Color(0xFF002F4B)

// --- Typography (Can be enhanced with custom fonts later) ---
private val AppTypography = Typography()

// --- Color Scheme Data Class ---
enum class GenderTheme { GIRL, BOY }

private fun getColorScheme(gender: GenderTheme): ColorScheme {
    return when (gender) {
        GenderTheme.GIRL -> lightColorScheme(
            primary = PinkPrimary,
            secondary = PinkSecondary,
            background = PinkBackground,
            onPrimary = PinkOnPrimary
        )
        GenderTheme.BOY -> lightColorScheme(
            primary = BluePrimary,
            secondary = BlueSecondary,
            background = BlueBackground,
            onPrimary = BlueOnPrimary
        )
    }
}

@Composable
fun FillMyCycleTheme(
    gender: GenderTheme = GenderTheme.GIRL,
    content: @Composable () -> Unit
) {
    val colorScheme = getColorScheme(gender)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}



