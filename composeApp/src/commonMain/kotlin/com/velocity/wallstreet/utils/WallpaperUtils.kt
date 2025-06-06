package com.velocity.wallstreet.utils

import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers

/**
 * Platform-specific interface for wallpaper operations. Alternate to expect/actual
 * Each platform (Android, Desktop) will provide its own implementation.
 */
interface WallpaperFunctions {

    suspend fun setWallpaper(imageUrl: String, type: WallpaperType): Result<Unit>

    suspend fun downloadImage(imageUrl: String): Result<Unit>
}

fun extractUniqueCategories(wallpapers: List<Model>): List<String> {
    return wallpapers.map { it.category }.distinct()
}