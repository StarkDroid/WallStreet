package com.velocity.wallstreet.utils

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.win32.W32APIOptions
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URI

actual fun getAppVersion(context: Any): String {
    return (System.getProperty("app.version"))
}

private interface User32 : Library {
    companion object {
        val INSTANCE = Native.load("user32", User32::class.java, W32APIOptions.DEFAULT_OPTIONS)
        const val SPI_SETDESKWALLPAPER = 20
        const val SPIF_UPDATEINIFILE = 0x01
        const val SPIF_SENDCHANGE = 0x02
    }

    fun SystemParametersInfoW(uiAction: Int, uiParam: Int, pvParam: String, fWinIni: Int): Boolean
}

suspend fun setWindowsWallpaper(imagePath: String): Boolean = withContext(Dispatchers.IO) {
    try {
        User32.INSTANCE.SystemParametersInfoW(
            User32.SPI_SETDESKWALLPAPER,
            0,
            imagePath,
            User32.SPIF_UPDATEINIFILE or User32.SPIF_SENDCHANGE
        )
    } catch (e: Exception) {
        false
    }
}

suspend fun downloadFile(imageUrl: String): File = withContext(Dispatchers.IO) {
    var inputStream: java.io.InputStream? = null
    var outputStream: FileOutputStream? = null

    val fileUrl = Url(imageUrl)
    val fileName = fileUrl.segments.last()
    val downloadDirectory = File(System.getProperty("user.home") + File.separator + "Downloads")
    val downloadFile = File(downloadDirectory, fileName)

    try {
        if (!downloadFile.exists()) {
            inputStream = URI(imageUrl).toURL().openStream()
            outputStream = FileOutputStream(downloadFile)
            val byteArray = ByteArray(8192)
            var count: Int
            while (run {
                    count = inputStream.read(byteArray)
                    count
                } > 0) {
                outputStream.write(byteArray, 0, count)
            }
        }
        return@withContext downloadFile
    } catch (e: Exception) {
        throw e
    } finally {
        outputStream?.close()
        inputStream?.close()
    }
}

