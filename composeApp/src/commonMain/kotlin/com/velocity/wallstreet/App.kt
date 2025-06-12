package com.velocity.wallstreet

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import com.velocity.wallstreet.theme.AppTheme
import com.velocity.wallstreet.ui.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {
    AppTheme {
        MainScreen()
    }
}