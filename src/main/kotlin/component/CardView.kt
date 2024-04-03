package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import data.Desktop
import utils.AsyncImage
import utils.handCursor
import utils.openFile
import java.net.URL

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardView(wallpapers: Desktop) {
    var cornerRadius by remember { mutableStateOf(8.dp) }
    var elevation by remember { mutableStateOf(4.dp) }
    var offset by remember { mutableStateOf(IntOffset(0, 0)) }
    var isHovering by remember { mutableStateOf(false) }

    // Init the hover modifications
    val hoverModifier = Modifier
        .pointerHoverIcon(handCursor())
        .onPointerEvent(PointerEventType.Enter) {
            cornerRadius = 16.dp
            elevation = 20.dp
            offset = IntOffset(0, -8)
            isHovering = true
        }
        .onPointerEvent(PointerEventType.Exit) {
            cornerRadius = 8.dp
            elevation = 4.dp
            offset = IntOffset(0, 0)
            isHovering = false
        }
        .offset { offset }

    Card(
        modifier = Modifier
            .width(500.dp)
            .height(250.dp)
            .padding(8.dp)
            .then(hoverModifier)
            .clickable {
                if (wallpapers.imageUrl.isNotEmpty()) {
                    openFile(wallpapers.imageUrl)
                }
            },
        shape = RoundedCornerShape(cornerRadius),
        elevation = elevation,
    ) {
        Box {
            val bitmap = useResource("no_wallpaper.png") {
                loadImageBitmap(it)
            }

            val downloadIcon = useResource("wallpaper_download_icon.svg") {
                loadSvgPainter(it, LocalDensity.current)
            }

            if (wallpapers.imageUrl.isEmpty()) {
                Image(
                    bitmap,
                    "No image available",
                    modifier = Modifier.size(100.dp).align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    load = { loadImageBitmapUrl(wallpapers.imageUrl) },
                    painterFor = { remember { BitmapPainter(it) } },
                    modifier = Modifier
                        .blur(if (isHovering) 10.dp else 0.dp)
                        .alpha(if (isHovering) 0.7f else 1f),
                    contentDescription = "Wallpaper thumbnail",
                )

                if (isHovering) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            downloadIcon,
                            "Download wallpaper icon",
                            modifier = Modifier.size(64.dp)
                        )

                        Text(
                            "Download",
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }

            if (isHovering.not()) {
                Text(wallpapers.wallpaperName)
            }
        }
    }
}

/**
* Helper function to load image using URL
*/
fun loadImageBitmapUrl(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)
