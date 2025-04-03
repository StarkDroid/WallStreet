package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import com.velocity.wallstreet.data.model.Model

@Composable
expect fun GridView(wallpapers: List<Model>, onImageClick: (String) -> Unit, gridState: LazyGridState)