package com.velocity.wallstreet.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.velocity.wallstreet.R
import com.velocity.wallstreet.ui.component.BottomSheetDialog
import com.velocity.wallstreet.utils.setWallpaperAction
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.res.stringResource as stringResourceCompose
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.wallpaper_screen_button_label
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc


@Composable
fun WallpaperViewScreen(
    imageUrl: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc)
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(innerPadding)
                    .padding(start = 16.dp, top = 16.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.TwoTone.ArrowBackIosNew,
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = stringResourceCompose(R.string.wallpaper_screen_desc_back_button)
                )
            }

            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 21.dp)
                    .size(48.dp),
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
                        setWallpaperAction(
                            imageUrl = imageUrl,
                            context = context,
                            type = type
                        )
                    },
                )
            }
        }
    }
}