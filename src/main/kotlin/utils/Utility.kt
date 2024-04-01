package utils

import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.pointer.PointerIcon
import io.ktor.http.*
import org.jetbrains.skia.Image
import java.awt.Cursor
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun handCursor() = PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))

fun loadPicture(url: String) =
    Image.makeFromEncoded(URL(url).readBytes())
        .toComposeImageBitmap()

fun openFile(url: String) {
    val fileUrl = Url(url)
    val fileName = fileUrl.pathSegments.last()
    val downloadDirectory = File(System.getProperty("user.home") + File.separator + "Downloads")
    val downloadFile = File(downloadDirectory, fileName)
    if (!downloadFile.exists()) {
        val inputStream = URL(url).openStream()
        val outputStream = FileOutputStream(downloadFile)
        var byteArray = ByteArray(1024)
        var count: Int
        while (run {
                count = inputStream.read(byteArray)
                count
            } > 0) {
            outputStream.write(byteArray, 0, count)
        }
        outputStream.close()
        inputStream.close()
        Desktop.getDesktop().open(downloadFile)
    }
}