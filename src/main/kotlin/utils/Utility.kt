package utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Cursor
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

/**
* Creates the pointed finger icon while hover
*/
fun handCursor() = PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))

/**
 * Helper method to download and open the chosen wallpaper in default image viewer
 */
fun openFile(url: String) {
    val fileUrl = Url(url)
    val fileName = fileUrl.pathSegments.last()
    val downloadDirectory = File(System.getProperty("user.home") + File.separator + "Downloads")
    val downloadFile = File(downloadDirectory, fileName)
    if (!downloadFile.exists()) {
        val inputStream = URL(url).openStream()
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
        Desktop.getDesktop().open(downloadFile)
    }
}

/**
 * Composable to load images Asynchronously
 * Avoids biting UI performance
 */
@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

/**
 * Load Fonts from here
 */
val displayFont = FontFamily(Font("font/Outfit-Medium.ttf"))
val brandFont = FontFamily(Font("font/Pacifico.ttf"))