package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.data.model.Model

@Composable
actual fun GridView(
    wallpapers: List<Model>,
    onImageClick: (String) -> Unit,
    gridState: LazyGridState,
) {

    if (wallpapers.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                state = gridState,
                columns = GridCells.Adaptive(minSize = 400.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(wallpapers) { wallpaper ->
                    CardView(
                        wallpaper, onImageClick,
                    )
                }
            }

            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp),
                adapter = rememberScrollbarAdapter(gridState),
                style = defaultScrollbarStyle().copy(
                    minimalHeight = 40.dp,
                    hoverColor = MaterialTheme.colorScheme.onBackground,
                    unhoverColor = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                )
            )
        }
    }
}