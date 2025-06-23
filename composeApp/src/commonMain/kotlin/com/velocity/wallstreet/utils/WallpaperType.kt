package com.velocity.wallstreet.utils

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.ic_both_wallpaper
import wallstreet.composeapp.generated.resources.ic_homescreen_wallpaper
import wallstreet.composeapp.generated.resources.ic_lockscreen_wallpaper
import wallstreet.composeapp.generated.resources.wallpaper_type_both
import wallstreet.composeapp.generated.resources.wallpaper_type_homescreen
import wallstreet.composeapp.generated.resources.wallpaper_type_lockscreen

sealed class WallpaperType(val label: StringResource, val iconRes: DrawableResource) {
    data object HomeScreen: WallpaperType(
        Res.string.wallpaper_type_homescreen,
        Res.drawable.ic_homescreen_wallpaper
    )
    data object LockScreen: WallpaperType(
        Res.string.wallpaper_type_lockscreen,
        Res.drawable.ic_lockscreen_wallpaper
    )
    data object Both: WallpaperType(
        Res.string.wallpaper_type_both,
        Res.drawable.ic_both_wallpaper
    )

    companion object {
        val values = listOf(HomeScreen, LockScreen, Both)
    }
}