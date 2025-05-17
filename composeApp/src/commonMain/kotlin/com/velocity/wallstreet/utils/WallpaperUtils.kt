package com.velocity.wallstreet.utils

import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers

/**
 * Get a platform-specific list of wallpapers from the wallpaper data.
 * Android will return mobile wallpapers, Desktop will return desktop wallpapers.
 */
expect fun getWallpaperList(wallpaperData: Wallpapers): List<Model>

/**
 * Platform-specific interface for wallpaper operations. Alternate to expect/actual
 * Each platform (Android, Desktop) will provide its own implementation.
 */
interface WallpaperFunctions {

    suspend fun setWallpaper(imageUrl: String, type: WallpaperType): Result<Unit>

    suspend fun downloadImage(imageUrl: String): Result<String>
}

fun extractUniqueCategories(wallpapers: List<Model>): List<String> {
    return wallpapers.map { it.category }.distinct()
}