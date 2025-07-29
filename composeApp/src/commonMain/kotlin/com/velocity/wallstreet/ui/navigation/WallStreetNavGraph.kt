package com.velocity.wallstreet.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WallStreetNavGraph(
    sharedTransitionScope: SharedTransitionScope
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN_ROUTE
    ) {
        mainScreen(
            sharedTransitionScope = sharedTransitionScope,
            onImageClick = navController::navigateToWallpaperScreen
        )

        wallpaperViewScreen(
            sharedTransitionScope = sharedTransitionScope,
            onBackClick = navController::popBackStack
        )
    }
}