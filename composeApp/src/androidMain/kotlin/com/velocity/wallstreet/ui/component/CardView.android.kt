package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.velocity.wallstreet.data.model.Model
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.no_thumbnail_desc
import wallstreet.composeapp.generated.resources.no_wallpaper
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc

@Composable
actual fun CardView(wallpapers: Model, onImageClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .height(350.dp)
            .padding(8.dp)
            .clickable {
                if (wallpapers.imageUrl.isNotEmpty()) {
                    onImageClick(wallpapers.imageUrl)
                }
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc),
                )
            }
        }
    }
}