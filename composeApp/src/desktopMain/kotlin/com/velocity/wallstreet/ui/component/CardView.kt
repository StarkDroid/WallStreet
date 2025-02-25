package com.velocity.wallstreet.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.velocity.wallstreet.data.model.Desktop
import com.velocity.wallstreet.utils.Constants
import com.velocity.wallstreet.utils.openFile
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.no_wallpaper
import wallstreet.composeapp.generated.resources.wallpaper_download_icon

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun CardView(wallpapers: Desktop) {
    var cornerRadius by remember { mutableStateOf(8.dp) }
    var elevation by remember { mutableStateOf(4.dp) }
    var isHovering by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val offset by animateFloatAsState(
        targetValue = if (isHovering) -8f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    val hoverModifier = Modifier
        .pointerHoverIcon(icon = PointerIcon.Hand)
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
                    coroutineScope.launch {
                        openFile(wallpapers.imageUrl)
                    }
                }
            },
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (wallpapers.imageUrl.isEmpty()) {
                Image(
                    painter = painterResource(Res.drawable.no_wallpaper),
                    Constants.no_thumbnail_desc,
                    modifier = Modifier.size(100.dp).align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = wallpapers.imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(if (isHovering) 10.dp else 0.dp)
                        .alpha(if (isHovering) 0.7f else 1f),
                    contentScale = ContentScale.Crop,
                    contentDescription = Constants.wallpaper_thumbnail_desc,
                )

                if (isHovering) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.wallpaper_download_icon),
                            contentDescription = Constants.download_icon_desc,
                            modifier = Modifier.size(64.dp)
                        )

                        Text(
                            Constants.download_text,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (isHovering.not()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                        .background(Color(0xFF8B93FF), RoundedCornerShape(0.dp, 8.dp, 0.dp, 0.dp))
                ) {
                    Text(
                        text = wallpapers.wallpaperName,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    }
}
