package com.velocity.wallstreet.utils

import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.net.URI

/**
 * Helper method to download and open the chosen wallpaper in default image viewer
 */
suspend fun openFile(url: String) {
    withContext(Dispatchers.IO) {
        val fileUrl = Url(url)
        val fileName = fileUrl.pathSegments.last()
        val downloadDirectory = File(System.getProperty("user.home") + File.separator + "Downloads")
        val downloadFile = File(downloadDirectory, fileName)
        if (!downloadFile.exists()) {
            val inputStream = URI(url).toURL().openStream()
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