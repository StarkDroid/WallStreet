package com.velocity.wallstreet.utils

import android.app.WallpaperManager

enum class WallpaperType(val flag: Int) {
    HOMESCREEN(WallpaperManager.FLAG_SYSTEM),
    LOCKSCREEN(WallpaperManager.FLAG_LOCK),
    BOTH(WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
}