package com.velocity.wallstreet.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import com.velocity.wallstreet.ui.component.BottomSheetDialog
import com.velocity.wallstreet.utils.setWallpaper
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc

@Composable
fun WallpaperViewScreen(imageUrl: String) {
    val context = LocalContext.current

    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AsyncImage(
            model = imageUrl,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc)
        )

        Button(
            onClick = {
               showBottomSheet = true
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(WindowInsets.navigationBars.asPaddingValues())
        ) {
            Text("Set Wallpaper")
        }

        if (showBottomSheet) {
            BottomSheetDialog(
                onDismissRequest = { showBottomSheet = false },
                bottomSheetActions = { setWallpaper(imageUrl = imageUrl, context = context) },
            )
        }
    }
}