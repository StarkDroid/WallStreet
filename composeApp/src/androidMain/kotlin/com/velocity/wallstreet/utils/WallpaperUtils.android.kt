package com.velocity.wallstreet.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.velocity.wallstreet.data.WallpaperApiClient
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

fun setWallpaper(imageUrl: String, context: Context) {
    val httpClient = HttpClient()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = httpClient.get(imageUrl)
            val imageBytes = response.readRawBytes()

            val contentType = response.contentType()?.toString() ?: "image/jpeg"
            val fileExtension = when {
                contentType.contains("webp") -> "webp"
                contentType.contains("png") -> "png"
                else -> "jpg"
            }

            val tempFile = File.createTempFile("wallpaper", ".$fileExtension", context.cacheDir)
            FileOutputStream(tempFile).use {
                it.write(imageBytes)
            }

            withContext(Dispatchers.Main) {
                val intent = Intent(Intent.ACTION_SET_WALLPAPER)
                intent.setDataAndType(Uri.fromFile(tempFile), contentType)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.putExtra("mimeType", contentType)
                context.startActivity(Intent.createChooser(intent, "Set Wallpaper"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpClient.close()
        }
    }
}

actual fun getWallpaperList(wallpaperData: Wallpapers): List<Model> {
    return wallpaperData.mobile
}