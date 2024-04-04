package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.Desktop
import data.WallpaperApiClient
import io.ktor.client.plugins.*
import kotlinx.coroutines.launch
import ui.component.BottomBarCredits
import ui.component.GridView
import utils.Constants
import utils.brandFont

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

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(100.dp),
                backgroundColor = Color(0xFFEEEEEE),
                elevation = 0.dp,
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = Constants.mainscreen_title_text,
                            fontFamily = brandFont,
                            style = MaterialTheme.typography.h3
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(30.dp),
                backgroundColor = Color(0xFFEEEEEE),
            ) {
                BottomBarCredits()
            }
        },
    ) {
        GridView(wallpapers)
    }
}