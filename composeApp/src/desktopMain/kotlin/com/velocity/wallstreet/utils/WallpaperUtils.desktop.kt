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
                PlatformUtils.isWindows() -> setWindowsWallpaper(downloadFile(imageUrl).absolutePath)
                PlatformUtils.isMacOS() -> {
                    val script = """
                                tell application "System Events"
                                    set picture of every desktop to "${downloadFile(imageUrl).absolutePath}"
                                end tell
                            """.trimIndent()

                    withContext(Dispatchers.IO) {
                        val process = ProcessBuilder("osascript", "-e", script).start()
                        process.waitFor()
                    }
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