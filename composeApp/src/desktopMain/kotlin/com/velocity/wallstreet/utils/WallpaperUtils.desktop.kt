package com.velocity.wallstreet.utils

import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.net.URI

/**
 * Helper method to download and open the chosen wallpaper in default image viewer
 */
fun setWallpaper(wallpaper: Model) {
    CoroutineScope(Dispatchers.IO).launch {
        val fileUrl = Url(wallpaper.imageUrl)
        val fileName = fileUrl.segments.last()
        val downloadDirectory = File(System.getProperty("user.home") + File.separator + "Downloads")
        val downloadFile = File(downloadDirectory, fileName)
        if (!downloadFile.exists()) {
            val inputStream = URI(wallpaper.imageUrl).toURL().openStream()
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
        if (downloadFile.exists()) {
            Desktop.getDesktop().open(downloadFile)
        }
    }
}

actual fun getWallpaperList(wallpaperData: Wallpapers): List<Model> {
    return wallpaperData.desktop
}