package com.velocity.wallstreet

import androidx.compose.runtime.Composable
import com.velocity.wallstreet.theme.AppTheme
import com.velocity.wallstreet.ui.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        MainScreen()
    }
}