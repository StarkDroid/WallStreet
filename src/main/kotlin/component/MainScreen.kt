package component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import data.Desktop
import data.WallpaperApiClient
import io.ktor.client.plugins.*
import kotlinx.coroutines.launch

@Preview
@Composable
fun MainScreen() {
    var wallpapers by remember { mutableStateOf(emptyList<Desktop>()) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                val wallpaperData = WallpaperApiClient.getWallpapers()
                wallpapers = wallpaperData.desktop
            } catch (e: ClientRequestException) {
                println("Error fetching data: ${e.message}")
            }
        }
    }

    GridView(wallpapers)

}