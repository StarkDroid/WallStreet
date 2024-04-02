package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import data.Desktop
import utils.handCursor
import utils.loadPicture
import utils.openFile

@Composable
fun CardView(wallpapers: Desktop) {
    Card(
        modifier = Modifier
            .width(400.dp)
            .height(200.dp)
            .padding(8.dp)
            .pointerHoverIcon(handCursor())
            .clickable {
                if (wallpapers.imageUrl.isNotEmpty()) {
                    openFile(wallpapers.imageUrl)
                }
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
    ) {
        Box {
            val bitmap = useResource("no_wallpaper.png") {
                loadImageBitmap(it)
            }

            if (wallpapers.imageUrl.isEmpty()) {
                Image(
                    bitmap,
                    "No image available",
                    modifier = Modifier.size(100.dp).align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    loadPicture(wallpapers.imageUrl),
                    "Wallpaper thumbnail",
                    contentScale = ContentScale.Crop
                )
            }
            Text(wallpapers.wallpaperName)
        }
    }
}