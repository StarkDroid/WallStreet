package com.velocity.wallstreet.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.velocity.wallstreet.theme.AppTheme
import com.velocity.wallstreet.ui.MainScreen
import com.velocity.wallstreet.ui.WallpaperViewScreen

sealed class NavRoute(val route: String) {
    data object MainScreen : NavRoute("mainScreen")
    data object WallpaperViewScreen : NavRoute("wallpaperViewScreen/{imageUrl}") {
        fun createRoute(imageUrl: String) = "wallpaperViewScreen/${Uri.encode(imageUrl)}"
    }
}

@Composable
fun WallStreetNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = NavRoute.MainScreen.route) {
        composable(NavRoute.MainScreen.route) {
            AppTheme {
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
            WallpaperViewScreen(imageUrl)
        }
    }
}