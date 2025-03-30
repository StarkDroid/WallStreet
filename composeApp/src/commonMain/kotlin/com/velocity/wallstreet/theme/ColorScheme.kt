package com.velocity.wallstreet.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Light theme color scheme
val lightColorScheme = lightColorScheme(
    primary = Color(0xFF90EE90),
    onPrimary = Color.Black,
    primaryContainer = Color.Black,
    secondary = Color(0xFF69D2E7),
    onSecondary = Color.White,
    tertiary = Color(0xFFA388EE),
    background = Color(0xFFFEF2E8),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)

// Dark theme color scheme
val darkColorScheme = darkColorScheme(
    primary = Color(0xFF90EE90),
    onPrimary = Color.Black,
    primaryContainer = Color.Black,
    secondary = Color(0xFF69D2E7),
    tertiary = Color(0xFFA388EE),
    onSecondary = Color.White,
    background = Color(0xFF374151),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFFFFFFF),
    error = Color(0xFFCF6679),
    onError = Color.Black
)