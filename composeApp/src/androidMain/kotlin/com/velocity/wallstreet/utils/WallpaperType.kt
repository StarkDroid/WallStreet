package com.velocity.wallstreet.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Download
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.Wallpaper
import androidx.compose.ui.graphics.vector.ImageVector
import com.velocity.wallstreet.R

sealed class WallpaperType(@StringRes val label: Int, val iconRes: ImageVector ) {
    data object HomeScreen: WallpaperType(R.string.wallpaper_type_homescreen, Icons.TwoTone.Home)
    data object LockScreen: WallpaperType(R.string.wallpaper_type_lockscreen, Icons.TwoTone.Lock)
    data object Both: WallpaperType(R.string.wallpaper_type_both, Icons.TwoTone.Wallpaper)
    data object DownloadOnly : WallpaperType(R.string.wallpaper_type_download, Icons.TwoTone.Download)

    companion object {
        val values = listOf(HomeScreen, LockScreen, Both, DownloadOnly)
    }
}

