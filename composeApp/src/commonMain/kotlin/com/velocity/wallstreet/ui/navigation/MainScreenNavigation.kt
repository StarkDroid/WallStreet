package com.velocity.wallstreet.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.velocity.wallstreet.theme.LocalAnimatedVisibilityScope
import com.velocity.wallstreet.theme.LocalSharedTransitionScope
import com.velocity.wallstreet.ui.screen.MainScreen

const val MAIN_SCREEN_ROUTE = "mainScreen"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainScreen(
    sharedTransitionScope: SharedTransitionScope,
    onImageClick: (String) -> Unit,
) {
    composable(route = MAIN_SCREEN_ROUTE) {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides sharedTransitionScope,
            LocalAnimatedVisibilityScope provides this
        ) {
            MainScreen(
                onImageClick = onImageClick
            )
        }
    }
}