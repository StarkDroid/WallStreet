package com.velocity.wallstreet.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.LocalPlatformContext
import com.velocity.wallstreet.ui.component.AnimatedHeaderText
import com.velocity.wallstreet.ui.component.AppHeader
import com.velocity.wallstreet.ui.component.BottomBarCredits
import com.velocity.wallstreet.ui.component.CategoryButton
import com.velocity.wallstreet.ui.component.GridView
import com.velocity.wallstreet.ui.component.LoadingIndicator
import com.velocity.wallstreet.utils.NeoBrutalistShapes
import com.velocity.wallstreet.utils.PlatformUtils
import com.velocity.wallstreet.utils.extractUniqueCategories
import com.velocity.wallstreet.utils.getAppVersion
import com.velocity.wallstreet.viewmodel.MainViewModel
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.update_available_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel { MainViewModel() },
    onImageClick: (String) -> Unit
) {
    val wallpapers by viewModel.wallpapers
    val config by viewModel.config
    val isLoading by viewModel.isLoading

    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val collapseFraction by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }
    val updateUrl = remember { mutableStateOf("") }

    val categories = extractUniqueCategories(wallpapers)
    val filteredWallpapers = if (selectedCategory != null) {
        wallpapers.filter { it.category == selectedCategory }
    } else {
        wallpapers
    }

    val animatedCornerRadius by animateDpAsState(
        targetValue = if (collapseFraction == 1f) 0.dp else NeoBrutalistShapes.Rounded,
        animationSpec = tween(durationMillis = 200)
    )

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

    LaunchedEffect(config) {
        config?.let {
            updateUrl.value = when {
                PlatformUtils.isMacOS() -> it.macUpdateUrl
                PlatformUtils.isWindows() -> it.windowsUpdateUrl
                PlatformUtils.isLinux() -> it.linuxUpdateUrl
                else -> it.androidUpdateUrl
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
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
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(25.dp),
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                BottomBarCredits()
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(
                        topStart = animatedCornerRadius,
                        topEnd = animatedCornerRadius
                    )
                )
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LoadingIndicator()
                    }
                }

                else -> {
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
    }
}