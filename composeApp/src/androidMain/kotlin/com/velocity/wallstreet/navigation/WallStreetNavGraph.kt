package com.velocity.wallstreet.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.velocity.wallstreet.ui.MainScreen
import com.velocity.wallstreet.ui.WallpaperViewScreen
import com.velocity.wallstreet.ui.component.BottomSheetContent
import com.velocity.wallstreet.ui.component.NeoBrutalistBottomSheet
import com.velocity.wallstreet.utils.LocalAnimatedVisibilityScope
import com.velocity.wallstreet.utils.LocalSharedTransitionScope
import com.velocity.wallstreet.viewmodel.OperationResult
import com.velocity.wallstreet.viewmodel.WallpaperScreenViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

sealed class NavRoute(val route: String) {
    data object MainScreen : NavRoute("mainScreen")
    data object WallpaperViewScreen : NavRoute("wallpaperViewScreen/{imageUrl}") {
        fun createRoute(imageUrl: String) = "wallpaperViewScreen/${Uri.encode(imageUrl)}"
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WallStreetNavGraph() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(navController, startDestination = NavRoute.MainScreen.route) {
            composable(NavRoute.MainScreen.route) {
                CompositionLocalProvider(
                    LocalSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalAnimatedVisibilityScope provides this@composable
                ) {
                    MainScreen { imageUrl ->
                        navController.navigate(NavRoute.WallpaperViewScreen.createRoute(imageUrl))
                    }
                }
            }

            composable(
                route = NavRoute.WallpaperViewScreen.route,
                arguments = listOf(navArgument("imageUrl") { type = NavType.StringType })
            ) { navBackStackEntry ->

                val imageUrl = navBackStackEntry.arguments?.getString("imageUrl") ?: ""

                val wallpaperViewModel = koinViewModel<WallpaperScreenViewModel>(
                    parameters = { parametersOf(imageUrl) }
                )

                val viewState by wallpaperViewModel.viewState.collectAsStateWithLifecycle()

                WallpaperViewScreen(
                    viewState = viewState,
                    onImageLoadSuccess = wallpaperViewModel::onImageLoaded,
                    onToggleBottomSheet = wallpaperViewModel::toggleBottomSheet,
                    onDownloadWallpaper = wallpaperViewModel::downloadWallpaper,
                    onBackClick = { navController.popBackStack() },
                    animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )

                if (viewState.showBottomSheet) {
                    NeoBrutalistBottomSheet(
                        cornerRadius = 8.dp,
                        onDismissRequest = {
                            if (viewState.applyWallpaperState !is OperationResult.Loading) {
                                wallpaperViewModel.toggleBottomSheet(false)
                            }
                        }
                    ) {
                        BottomSheetContent(
                            result = viewState.applyWallpaperState,
                            onApplyWallpaper = wallpaperViewModel::applyWallpaper,
                        )
                    }
                }
            }
        }
    }
}