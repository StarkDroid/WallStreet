package com.velocity.wallstreet.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import com.velocity.wallstreet.data.WallpaperApiClient
import com.velocity.wallstreet.data.model.Config
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.ui.component.AnimatedHeaderText
import com.velocity.wallstreet.ui.component.AppHeader
import com.velocity.wallstreet.ui.component.BottomBarCredits
import com.velocity.wallstreet.ui.component.CategoryButton
import com.velocity.wallstreet.ui.component.GridView
import com.velocity.wallstreet.utils.PlatformUtils
import com.velocity.wallstreet.utils.extractUniqueCategories
import com.velocity.wallstreet.utils.getAppVersion
import com.velocity.wallstreet.utils.getWallpaperList
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.update_available_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onImageClick: (String) -> Unit) {
    var wallpapers by remember { mutableStateOf<List<Model>>(emptyList()) }
    var config by remember { mutableStateOf<Config?>(null) }
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val updateUrl = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                val wallpaperData = WallpaperApiClient.getWallpapers()

                wallpapers = getWallpaperList(wallpaperData).shuffled()
                config = wallpaperData.config

                updateUrl.value = when {
                    PlatformUtils.isMacOS() -> wallpaperData.config.macUpdateUrl
                    PlatformUtils.isWindows() -> wallpaperData.config.windowsUpdateUrl
                    PlatformUtils.isLinux() -> wallpaperData.config.linuxUpdateUrl
                    else -> wallpaperData.config.androidUpdateUrl
                }
            } catch (e: ClientRequestException) {
                println("Error fetching data: ${e.message}")
            }
        }
    }

    val categories = extractUniqueCategories(wallpapers)

    val filteredWallpapers = if (selectedCategory != null) {
        wallpapers.filter { it.category == selectedCategory }
    } else {
        wallpapers
    }

    val hyperLinkText = buildAnnotatedString {
        withLink(
            LinkAnnotation.Url(
                url = updateUrl.value,
                TextLinkStyles(
                    style = SpanStyle(
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            )
        ) {
            append(stringResource(Res.string.update_available_text))
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    config?.let { config ->
                        AppHeader(
                            downloadUrl = hyperLinkText,
                            currentAppVersion = getAppVersion(LocalPlatformContext.current),
                            latestAppVersion = config.appUpdateVersion
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
            AnimatedHeaderText(scrollBehavior = scrollBehavior)
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(60.dp),
            ) {
                BottomBarCredits()
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                CategoryButton(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = if (selectedCategory == category) null else category
                    }
                )

                GridView(
                    wallpapers = filteredWallpapers,
                    onImageClick = onImageClick
                )
            }
        }
    }
}