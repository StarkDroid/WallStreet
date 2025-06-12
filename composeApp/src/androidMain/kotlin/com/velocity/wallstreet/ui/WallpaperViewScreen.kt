package com.velocity.wallstreet.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.velocity.wallstreet.R
import com.velocity.wallstreet.ui.component.BottomBarContent
import com.velocity.wallstreet.ui.component.LoadingIndicator
import com.velocity.wallstreet.ui.component.NeoBrutalistButton
import com.velocity.wallstreet.viewmodel.WallpaperScreenViewState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.ic_back
import wallstreet.composeapp.generated.resources.wallpaper_thumbnail_desc
import androidx.compose.ui.res.stringResource as stringResourceCompose


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WallpaperViewScreen(
    viewState: WallpaperScreenViewState,
    onImageLoadSuccess: () -> Unit,
    onToggleBottomSheet: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
) {

    Scaffold(
        bottomBar = {
            if (!viewState.isLoading) {
                BottomBarContent(onToggleBottomSheet)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            if (viewState.isLoading) {
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
                        .data(viewState.imageUrl)
                        .crossfade(true)
                        .listener(
                            onSuccess = { _, _ -> onImageLoadSuccess() },
                            onError = { _, _ -> onImageLoadSuccess() }
                        )
                        .build(),
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedBounds(
                            rememberSharedContentState(key = viewState.imageUrl),
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
        }
    }
}