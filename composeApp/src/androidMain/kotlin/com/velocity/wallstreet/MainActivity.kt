package com.velocity.wallstreet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowCompat
import com.velocity.wallstreet.navigation.WallStreetNavGraph
import com.velocity.wallstreet.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val windowDecor = WindowCompat.getInsetsController(window, window.decorView)

        setContent {
            windowDecor.isAppearanceLightStatusBars = !isSystemInDarkTheme()

            AppTheme {
                WallStreetNavGraph()
            }
        }
    }
}