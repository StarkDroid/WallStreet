package com.velocity.wallstreet.utils

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WallpaperUtilsAndroid(
    private val context: Context
) : WallpaperFunctions {
    override suspend fun setWallpaper(
        imageUrl: String,
        type: WallpaperType
    ): Result<Unit> {
        return try {
            val bitmapResult = downloadBitmap(imageUrl = imageUrl)

            if (bitmapResult.isFailure) {
                return Result.failure(bitmapResult.exceptionOrNull() ?: Exception("Failed to download image"))
            }

            val bitmap = bitmapResult.getOrThrow()
            val wallpaperManager = WallpaperManager.getInstance(context)

            when (type) {
                is WallpaperType.HomeScreen -> {
                    withContext(Dispatchers.IO) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    }
                }

                WallpaperType.LockScreen -> {
                    withContext(Dispatchers.IO) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    }
                }

                WallpaperType.Both -> {
                    withContext(Dispatchers.IO) {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                        )
                    }
                }

                WallpaperType.DownloadOnly -> {
                    withContext(Dispatchers.IO) {
                        downloadImage(imageUrl = imageUrl)
                    }
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun downloadImage(imageUrl: String): Result<String> {
        return try {
            val bitmap = downloadBitmap(imageUrl = imageUrl).getOrThrow()

            val (format, extension) = getImageFormat(imageUrl)
            val fileName = "wallpaper_${System.currentTimeMillis()}.$extension"
            val mimeType = when (format) {
                Bitmap.CompressFormat.JPEG -> "image/jpeg"
                Bitmap.CompressFormat.PNG -> "image/png"
                else -> "image/jpeg"
            }

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/WallStreet")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val resolver = context.contentResolver
            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            imageUri?.let { uri ->
                resolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap.compress(format, 100, outputStream)
                }

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }


            Toast.makeText(context, "Wallpaper saved to gallery", Toast.LENGTH_SHORT).show()
            Result.success(imageUri.toString())
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to save image ${e.message}", Toast.LENGTH_LONG).show()
            Result.failure(e)
        }

    }
    
    private suspend fun downloadBitmap(imageUrl: String): Result<Bitmap> {
        return try {
            val httpClient = HttpClient()

            try {
                val response = httpClient.get(imageUrl)
                val imageBytes = response.readRawBytes()
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Result.success(bitmap)
            } finally {
                httpClient.close()
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getImageFormat(url: String): Pair<Bitmap.CompressFormat, String> {
        return when {
            url.endsWith(".png", true) -> Bitmap.CompressFormat.PNG to "png"
            else -> Bitmap.CompressFormat.JPEG to "jpg"
        }
    }
}

actual fun getWallpaperList(wallpaperData: Wallpapers): List<Model> {
    return wallpaperData.mobile
}
