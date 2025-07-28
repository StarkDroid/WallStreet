package com.velocity.wallstreet.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.velocity.wallstreet.ui.component.BottomSheetContent
import com.velocity.wallstreet.ui.component.NeoBrutalistBottomSheet
import com.velocity.wallstreet.ui.screen.WallpaperViewScreen
import com.velocity.wallstreet.viewmodel.OperationResult
import com.velocity.wallstreet.viewmodel.WallpaperScreenViewModel
import io.ktor.http.decodeURLPart
import io.ktor.http.encodeURLParameter
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

const val WALLPAPER_SCREEN_ROUTE = "wallpaperScreen"
private const val WALLPAPER_SCREEN_ARG_IMAGE = "imageUrl"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.wallpaperViewScreen(
    sharedTransitionScope: SharedTransitionScope,
    onBackClick: () -> Unit
) {
    composable(
        route = "$WALLPAPER_SCREEN_ROUTE/{$WALLPAPER_SCREEN_ARG_IMAGE}",
        arguments = listOf(navArgument(WALLPAPER_SCREEN_ARG_IMAGE) { type = NavType.StringType })
    ) { backStackEntry ->
        val imageUrl =
            backStackEntry.arguments?.read { getStringOrNull("imageUrl")?.decodeURLPart() }

        val wallpaperViewModel = koinViewModel<WallpaperScreenViewModel>(
            parameters = { parametersOf(imageUrl) }
        )

        val viewState by wallpaperViewModel.viewState.collectAsStateWithLifecycle()


        WallpaperViewScreen(
            viewState = viewState,
            onImageLoadSuccess = wallpaperViewModel::onImageLoaded,
            onToggleBottomSheet = wallpaperViewModel::toggleBottomSheet,
            onDownloadWallpaper = wallpaperViewModel::downloadWallpaper,
            onBackClick = onBackClick,
            animatedVisibilityScope = this,
            sharedTransitionScope = sharedTransitionScope,
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

fun NavController.navigateToWallpaperScreen(imageUrl: String) {
    navigate("$WALLPAPER_SCREEN_ROUTE/${imageUrl.encodeURLParameter()}")
}
