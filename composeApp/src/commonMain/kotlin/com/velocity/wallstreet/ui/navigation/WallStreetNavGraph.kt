package com.velocity.wallstreet.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WallStreetNavGraph() {
    SharedTransitionLayout {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = MAIN_SCREEN_ROUTE
        ) {
            mainScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                onImageClick = { imageUrl ->
                    navController.navigateToWallpaperScreen(imageUrl)
                }
            )
            
            wallpaperViewScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                onBackClick = navController::popBackStack
            )
        }
    }
}