package com.velocity.wallstreet.utils

import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers

expect fun getWallpaperList(wallpaperData: Wallpapers): List<Model>

fun extractUniqueCategories(wallpapers: List<Model>): List<String> {
    return wallpapers.map { it.category }.distinct()
}