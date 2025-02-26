package com.velocity.wallstreet.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Light theme color scheme
val lightColorScheme = lightColorScheme(
    primary = Color(0xFF2D336B),
    onPrimary = Color.White,
    secondary = Color(0xFF00879E),
    onSecondary = Color.White,
    background = Color(0xFFFFF2F2),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)

// Dark theme color scheme
val darkColorScheme = darkColorScheme(
    primary = Color(0xFFB2A5FF),
    onPrimary = Color.Black,
    secondary = Color(0xFF00879E),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFFFFFFF),
    error = Color(0xFFCF6679),
    onError = Color.Black
)