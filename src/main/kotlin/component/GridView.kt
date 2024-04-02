package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.Desktop

@Composable
fun GridView(wallpapers: List<Desktop>) {
    if (wallpapers.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 400.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(wallpapers) {
                    CardView(it)
                }
            }
        }
    } else {
        Text("Loading...")
    }
}