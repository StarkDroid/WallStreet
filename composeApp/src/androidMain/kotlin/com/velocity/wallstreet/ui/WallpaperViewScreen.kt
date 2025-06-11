package com.velocity.wallstreet.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
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
import com.velocity.wallstreet.viewmodel.OperationResult
import com.velocity.wallstreet.viewmodel.WallpaperScreenViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.ic_apply_wallpaper
import wallstreet.composeapp.generated.resources.ic_back
import wallstreet.composeapp.generated.resources.wallpaper_screen_button_label
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc
import androidx.compose.ui.res.stringResource as stringResourceCompose


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WallpaperViewScreen(
    operationResult: OperationResult?,
    viewModel: WallpaperScreenViewModel,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (!isLoading) {
                NeoBrutalistButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                    cornerRadius = 8.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_apply_wallpaper),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentDescription = stringResource(Res.string.wallpaper_screen_button_label)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = stringResource(Res.string.wallpaper_screen_button_label),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
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

            with(sharedTransitionScope) {
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
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedBounds(
                            rememberSharedContentState(key = viewModel.imageUrl),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        ),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(Res.string.wallpaper_thumbnail_desc)
                )
            }

            NeoBrutalistButton(
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(innerPadding)
                    .padding(start = 16.dp, top = 40.dp),
                onClick = onBackClick,
                cornerRadius = 8.dp
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_back),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = stringResourceCompose(R.string.wallpaper_screen_desc_back_button)
                )
            }

            if (showBottomSheet) {
                NeoBrutalistBottomSheet(
                    cornerRadius = 8.dp,
                    onDismissRequest = {
                        if (operationResult == null) {
                            showBottomSheet = false
                        }
                    }
                ) {
                    BottomSheetContent(
                        result = operationResult,
                        onApplyWallpaper = { type ->
                            viewModel.applyWallpaper(type)
                        },
                    )
                }
            }

            if (!isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .blur(12.dp, BlurredEdgeTreatment.Unbounded)
                        .align(Alignment.BottomCenter)
                        .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                )
            }
        }
    }
}