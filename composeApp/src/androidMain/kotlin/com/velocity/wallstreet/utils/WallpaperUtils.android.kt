package com.velocity.wallstreet.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun setWallpaper(imageUrl: String, context: Context, type: Int) {
    val httpClient = HttpClient()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = httpClient.get(imageUrl)
            val imageBytes = response.readRawBytes()

            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            withContext(Dispatchers.Main) {
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap, null, true, type)
                Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
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

actual fun getWallpaperList(wallpaperData: Wallpapers): List<Model> {
    return wallpaperData.mobile
}
