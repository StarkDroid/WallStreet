package com.velocity.wallstreet.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun getColorScheme(darkTheme: Boolean): ColorScheme?

// Light theme color scheme
val lightColorScheme = lightColorScheme(
    primary = Color(0xff428a42),
    onPrimary = Color.Black,
    inversePrimary = Color.Black,
    secondary = Color(0xff3a8b9b),
    onSecondary = Color.White,
    tertiary = Color(0xFFA388EE),
    background = Color(0xfffdfaf8),
    onBackground = Color.Black,
    surface = Color(0xfffdf1de),
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)

// Dark theme color scheme
val darkColorScheme = darkColorScheme(
    primary = Color(0xFF90EE90),
    onPrimary = Color.Black,
    inversePrimary = Color.White,
    secondary = Color(0xFF69D2E7),
    tertiary = Color(0xFFA388EE),
    onSecondary = Color.White,
    background = Color(0xff0b0e0e),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xff2e3036),
    onSurface = Color(0xFFFFFFFF),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

// Custom App colors
object AppColors {
    val successGreen = Color(0xff428f42)
}