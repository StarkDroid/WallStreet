package com.velocity.wallstreet.utils

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.R)
fun setWallpaperAction(imageUrl: String, context: Context, type: WallpaperType) {
    val httpClient = HttpClient()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = httpClient.get(imageUrl)
            val imageBytes = response.readRawBytes()

            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            val (format, extension) = getImageFormat(imageUrl)

            val wallpaperManager = WallpaperManager.getInstance(context)

            withContext(Dispatchers.Main) {
                when (type) {
                    WallpaperType.HomeScreen -> {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )
                        Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
                    }

                    WallpaperType.LockScreen -> {
                        WallpaperManager.getInstance(context)
                            .setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                        Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
                    }

                    WallpaperType.Both -> {
                        WallpaperManager.getInstance(context).setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                        )
                        Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
                    }

                    WallpaperType.DownloadOnly -> {
                        saveImageToGallery(context, bitmap, format, extension)
                        Toast.makeText(context, "Wallpaper saved successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
            }
        } finally {
            httpClient.close()
        }
    }
}

private fun saveImageToGallery(
    context: Context,
    bitmap: Bitmap,
    format: Bitmap.CompressFormat,
    extension: String
): Uri? {
    val fileName = "wallpaper_${System.currentTimeMillis()}.$extension"
    val mimeType = when (format) {
        Bitmap.CompressFormat.JPEG -> "image/jpeg"
        Bitmap.CompressFormat.PNG -> "image/png"
        else -> "image/jpeg"
    }

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val resolver = context.contentResolver
    val imageUri: Uri? =
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    imageUri?.let { uri ->
        resolver.openOutputStream(uri)?.use { outputStream ->
            bitmap.compress(format, 100, outputStream)
        }
    }

    return imageUri
}

private fun getImageFormat(url: String): Pair<Bitmap.CompressFormat, String> {
    return when {
        url.endsWith(".png", true) -> Bitmap.CompressFormat.PNG to "png"
        else -> Bitmap.CompressFormat.JPEG to "jpg"
    }
}


actual fun getWallpaperList(wallpaperData: Wallpapers): List<Model> {
    return wallpaperData.mobile
}
