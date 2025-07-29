package com.velocity.wallstreet

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import com.velocity.wallstreet.theme.AppTheme
import com.velocity.wallstreet.ui.navigation.WallStreetNavGraph

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    SharedTransitionLayout {
        AppTheme {
            WallStreetNavGraph(sharedTransitionScope = this@SharedTransitionLayout)
        }
    }
}