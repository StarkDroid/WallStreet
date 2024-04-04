package component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Desktop
import utils.*
import java.net.URL

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardView(wallpapers: Desktop) {
    var cornerRadius by remember { mutableStateOf(8.dp) }
    var elevation by remember { mutableStateOf(4.dp) }
    var isHovering by remember { mutableStateOf(false) }

    val offset by animateFloatAsState(
        targetValue = if (isHovering) -8f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    // Init the hover modifications
    val hoverModifier = Modifier
        .pointerHoverIcon(handCursor())
        .onPointerEvent(PointerEventType.Enter) {
            cornerRadius = 16.dp
            elevation = 20.dp
            isHovering = true
        }
        .onPointerEvent(PointerEventType.Exit) {
            cornerRadius = 8.dp
            elevation = 4.dp
            isHovering = false
        }
        .graphicsLayer {
            translationY = offset
        }

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
        Box(modifier = Modifier.fillMaxSize()) {
            val bitmap = useResource("no_wallpaper.png") {
                loadImageBitmap(it)
            }

            val downloadIcon = useResource("wallpaper_download_icon.svg") {
                loadSvgPainter(it, LocalDensity.current)
            }

            val authorIcon = useResource("ic_author.svg") {
                loadSvgPainter(it, LocalDensity.current)
            }

            if (wallpapers.imageUrl.isEmpty()) {
                Image(
                    bitmap,
                    Constants.no_thumbnail_desc,
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
                    contentDescription = Constants.wallpaper_thumbnail_desc,
                )

                if (isHovering) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            downloadIcon,
                            Constants.download_icon_desc,
                            modifier = Modifier.size(64.dp)
                        )

                        Text(
                            Constants.download_text,
                            fontFamily = displayFont,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }

            if (isHovering.not()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = wallpapers.wallpaperName,
                        fontFamily = displayFont,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(bottom = 12.dp)
                        .background(Color(0xFF8B93FF), RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            authorIcon,
                            Constants.author_icon_desc,
                            modifier = Modifier.size(18.dp).padding(start = 2.dp)
                        )

                        Text(
                            text = wallpapers.author,
                            fontFamily = displayFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
* Helper function to load image using URL
*/
fun loadImageBitmapUrl(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)
