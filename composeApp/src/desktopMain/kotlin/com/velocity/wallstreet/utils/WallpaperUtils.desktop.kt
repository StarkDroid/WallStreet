package com.velocity.wallstreet.utils

import io.ktor.http.*
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.net.URI

class WallpaperUtilsDesktop : WallpaperFunctions {

    override suspend fun setWallpaper(
        imageUrl: String,
        type: WallpaperType
    ): Result<Unit> {
        return try {
            val fileUrl = Url(imageUrl)
            val fileName = fileUrl.segments.last()
            val downloadDirectory = File(System.getProperty("user.home") + File.separator + "Downloads")
            val downloadFile = File(downloadDirectory, fileName)

            if (downloadFile.exists()) {
                downloadImage(imageUrl)
                Desktop.getDesktop().open(downloadFile)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to apply wallpaper"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun downloadImage(imageUrl: String): Result<String> {
        return try {
            val fileUrl = Url(imageUrl)
            val fileName = fileUrl.segments.last()
            val downloadDirectory = File(System.getProperty("user.home") + File.separator + "Downloads")
            val downloadFile = File(downloadDirectory, fileName)

            if (!downloadFile.exists()) {
                val inputStream = URI(imageUrl).toURL().openStream()
                val outputStream = FileOutputStream(downloadFile)
                val byteArray = ByteArray(1024)
                var count: Int
                while (run {
                        count = inputStream.read(byteArray)
                        count
                    } > 0) {
                    outputStream.write(byteArray, 0, count)
                }
                outputStream.close()
                inputStream.close()
            }

            Result.success(downloadFile.absolutePath)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}