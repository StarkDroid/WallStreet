package com.velocity.wallstreet.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getColorScheme(darkTheme: Boolean): ColorScheme? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkTheme) {
            dynamicDarkColorScheme(LocalContext.current)
        } else {
            dynamicLightColorScheme(LocalContext.current)
        }
    } else {
        null
    }
}