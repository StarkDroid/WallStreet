package com.velocity.wallstreet.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.velocity.wallstreet.ui.component.BottomSheetDialog
import com.velocity.wallstreet.utils.setWallpaper
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.wallpaper_screen_button_label
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
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .padding(WindowInsets.navigationBars.asPaddingValues()),
            onClick = {
                showBottomSheet = true
            }
        ) {
            Text(
                 text = stringResource(Res.string.wallpaper_screen_button_label),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (showBottomSheet) {
            BottomSheetDialog(
                onDismissRequest = { showBottomSheet = false },
                onApplyWallpaper = { type ->
                    setWallpaper(
                        imageUrl = imageUrl,
                        context = context,
                        type = type.flag
                    )
                },
            )
        }
    }
}