package com.velocity.wallstreet.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import com.velocity.wallstreet.utils.setWallpaper
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc

@Composable
fun WallpaperViewScreen(imageUrl: String) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        AsyncImage(
            model = imageUrl,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc)
        )

        Button(
            onClick = {
               setWallpaper(imageUrl, context)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Set Wallpaper")
        }
    }
}