package com.velocity.wallstreet.utils

import android.app.WallpaperManager
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.Wallpaper
import androidx.compose.ui.graphics.vector.ImageVector
import com.velocity.wallstreet.R

sealed class WallpaperType(val flag: Int, @StringRes val label: Int, val iconRes: ImageVector ) {
    object HomeScreen: WallpaperType(WallpaperManager.FLAG_SYSTEM, R.string.wallpaper_type_homescreen, Icons.TwoTone.Home)
    object LockScreen: WallpaperType(WallpaperManager.FLAG_SYSTEM, R.string.wallpaper_type_lockscreen, Icons.TwoTone.Lock)
    object Both: WallpaperType(WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK, R.string.wallpaper_type_both, Icons.TwoTone.Wallpaper)

    companion object {
        val values = listOf(HomeScreen, LockScreen, Both)
    }
}

