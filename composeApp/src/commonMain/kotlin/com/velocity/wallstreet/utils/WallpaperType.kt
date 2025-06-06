package com.velocity.wallstreet.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Download
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.Wallpaper
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.wallpaper_type_both
import wallstreet.composeapp.generated.resources.wallpaper_type_download
import wallstreet.composeapp.generated.resources.wallpaper_type_homescreen
import wallstreet.composeapp.generated.resources.wallpaper_type_lockscreen

sealed class WallpaperType(val label: StringResource, val iconRes: ImageVector) {
    data object HomeScreen: WallpaperType(
        Res.string.wallpaper_type_homescreen,
        Icons.TwoTone.Home
    )
    data object LockScreen: WallpaperType(
        Res.string.wallpaper_type_lockscreen,
        Icons.TwoTone.Lock
    )
    data object Both: WallpaperType(
        Res.string.wallpaper_type_both,
        Icons.TwoTone.Wallpaper
    )
    data object DownloadOnly : WallpaperType(
        Res.string.wallpaper_type_download,
        Icons.TwoTone.Download
    )

    companion object {
        val values = listOf(HomeScreen, LockScreen, Both, DownloadOnly)
    }
}