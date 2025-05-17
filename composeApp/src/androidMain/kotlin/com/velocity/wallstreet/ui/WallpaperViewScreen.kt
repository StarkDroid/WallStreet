package com.velocity.wallstreet.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBackIosNew
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.velocity.wallstreet.R
import com.velocity.wallstreet.ui.component.BottomSheetContent
import com.velocity.wallstreet.ui.component.LoadingIndicator
import com.velocity.wallstreet.ui.component.NeoBrutalistBottomSheet
import com.velocity.wallstreet.ui.component.NeoBrutalistButton
import com.velocity.wallstreet.viewmodel.WallpaperScreenViewModel
import com.velocity.wallstreet.viewmodel.WallpaperViewState
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.res.stringResource as stringResourceCompose
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.wallpaper_screen_button_label
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc


@Composable
fun WallpaperViewScreen(
    viewState: WallpaperViewState,
    viewModel: WallpaperScreenViewModel,
    onBackClick: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator()
                }
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(viewModel.imageUrl)
                    .crossfade(true)
                    .listener(
                        onStart = { isLoading = true },
                        onSuccess = { _, _ -> isLoading = false },
                        onError = { _, _ -> isLoading = false }
                    )
                    .build(),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc)
            )

            NeoBrutalistButton(
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(innerPadding)
                    .padding(start = 16.dp, top = 40.dp),
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.TwoTone.ArrowBackIosNew,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = stringResourceCompose(R.string.wallpaper_screen_desc_back_button)
                )
            }

            if (!isLoading) {
                NeoBrutalistButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 50.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.wallpaper_screen_button_label),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (showBottomSheet) {
                NeoBrutalistBottomSheet(
                    cornerRadius = 8.dp,
                    onDismissRequest = { showBottomSheet = false }
                ) {
                    BottomSheetContent(
                        onApplyWallpaper = { type ->
                            viewModel.applyWallpaper(type)
                        }
                    )
                }
            }
        }
    }
}