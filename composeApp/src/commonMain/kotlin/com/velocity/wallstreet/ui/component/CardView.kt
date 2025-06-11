package com.velocity.wallstreet.ui.component

import androidx.compose.runtime.Composable
import com.velocity.wallstreet.data.model.Model

@Composable
expect fun CardView(
    wallpapers: Model,
    onImageClick: (String) -> Unit,
)
