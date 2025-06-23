package com.velocity.wallstreet.ui.component

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.utils.LocalAnimatedVisibilityScope
import com.velocity.wallstreet.utils.LocalSharedTransitionScope
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.no_thumbnail_desc
import wallstreet.composeapp.generated.resources.no_wallpaper
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
actual fun CardView(
    wallpapers: Model,
    onImageClick: (String) -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    with(sharedTransitionScope) {
        NeoBrutalistCardView(
            onClick = {
                if (wallpapers.imageUrl.isNotEmpty()) {
                    onImageClick(wallpapers.imageUrl)
                }
            }
        ) {
            if (wallpapers.imageUrl.isEmpty()) {
                Image(
                    painter = painterResource(Res.drawable.no_wallpaper),
                    contentDescription = stringResource(Res.string.no_thumbnail_desc),
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(wallpapers.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedBounds(
                            rememberSharedContentState(key = wallpapers.imageUrl),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        )
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc),
                )
            }
        }
    }
}