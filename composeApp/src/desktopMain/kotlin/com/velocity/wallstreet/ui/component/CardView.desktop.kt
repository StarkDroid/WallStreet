package com.velocity.wallstreet.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.utils.setWallpaper
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.download_text
import wallstreet.composeapp.generated.resources.no_thumbnail_desc
import wallstreet.composeapp.generated.resources.no_wallpaper
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc

@Composable
actual fun CardView(
    wallpapers: Model,
    onImageClick: (String) -> Unit
) {
    var isHovering by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> isHovering = true
                is HoverInteraction.Exit -> isHovering = false
            }
        }
    }

    NeoBrutalistCardView(
        modifier = Modifier
            .width(500.dp)
            .height(250.dp)
            .hoverable(interactionSource = interactionSource),
        shadowColor = MaterialTheme.colorScheme.inversePrimary,
        onClick = {
            // Do nothing
        }
    ) {
        if (wallpapers.imageUrl.isEmpty()) {
            Image(
                painter = painterResource(Res.drawable.no_wallpaper),
                contentDescription = stringResource(Res.string.no_thumbnail_desc),
                modifier = Modifier.size(100.dp).align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(wallpapers.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxSize()
                    .blur(if (isHovering) 5.dp else 0.dp),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc),
            )

            AnimatedVisibility(
                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }),
                visible = isHovering,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                NeoBrutalistButton(
                    modifier = Modifier.pointerHoverIcon(icon = PointerIcon.Hand),
                    onClick = {
                        if (wallpapers.imageUrl.isNotEmpty()) {
                            setWallpaper(wallpapers)
                        }
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.download_text),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
