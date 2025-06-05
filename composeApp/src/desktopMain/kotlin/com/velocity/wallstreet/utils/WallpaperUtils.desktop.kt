package com.velocity.wallstreet.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Desktop

class WallpaperUtilsDesktop : WallpaperFunctions {

    override suspend fun setWallpaper(
        imageUrl: String,
        type: WallpaperType
    ): Result<Unit> {
        return try {
            when {
                type is WallpaperType.DownloadOnly -> downloadImage(imageUrl)
                else -> {
                    setWindowsWallpaper(downloadFile(imageUrl).absolutePath)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun downloadImage(imageUrl: String) = withContext(Dispatchers.IO) {
        try {
            downloadFile(imageUrl)
            Desktop.getDesktop().open(downloadFile(imageUrl))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}