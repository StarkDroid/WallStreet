package com.velocity.wallstreet.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.data.WallpaperApiClient
import com.velocity.wallstreet.data.model.Desktop
import com.velocity.wallstreet.ui.component.BottomBarCredits
import com.velocity.wallstreet.ui.component.GridView
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.launch
import com.velocity.wallstreet.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var wallpapers by remember { mutableStateOf(emptyList<Desktop>()) }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

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
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = Constants.mainscreen_title_text,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(30.dp),
            ) {
                BottomBarCredits()
            }
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            GridView(wallpapers)
        }
    }
    VerticalScrollbar(
        adapter = rememberScrollbarAdapter(scrollState),
    )
}