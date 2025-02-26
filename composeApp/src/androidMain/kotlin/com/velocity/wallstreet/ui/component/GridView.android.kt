package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.data.model.Desktop

@Composable
actual fun GridView(wallpapers: List<Desktop>) {
    val listState = rememberLazyGridState()

    if (wallpapers.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                state = listState,
                columns = GridCells.Adaptive(minSize = 400.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(wallpapers) {
                    CardView(it)
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingIndicator()
        }
    }
}