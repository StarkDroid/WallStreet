package com.velocity.wallstreet.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = getColorScheme(darkTheme) ?:
    if (darkTheme) darkColorScheme else lightColorScheme

    val rippleConfiguration = RippleConfiguration(
        color = MaterialTheme.colorScheme.primary,
        rippleAlpha = (RippleAlpha(0f,0f,0f,0f))
    )

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography(),
            content = content
        )
    }
}