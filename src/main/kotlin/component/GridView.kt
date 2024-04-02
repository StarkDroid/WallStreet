package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import data.Desktop
import utils.openFile
import utils.handCursor
import utils.loadPicture

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
                    Card(
                        modifier = Modifier
                            .width(400.dp)
                            .height(200.dp)
                            .padding(8.dp)
                            .pointerHoverIcon(handCursor())
                            .clickable {
                                if (it.imageUrl.isNotEmpty()) {
                                    openFile(it.imageUrl)
                                }
                            },
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                    ) {
                        Box {
                            val bitmap = useResource("no_wallpaper.png") {
                                loadImageBitmap(it)
                            }

                            if (it.imageUrl.isEmpty()) {
                                Image(
                                    bitmap,
                                    "No image available",
                                    modifier = Modifier.size(100.dp).align(Alignment.Center),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    loadPicture(it.imageUrl),
                                    "Wallpaper thumbnail",
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Text(it.wallpaperName)
                        }
                    }
                }
            }
        }
    } else {
        Text("Loading...")
    }
}