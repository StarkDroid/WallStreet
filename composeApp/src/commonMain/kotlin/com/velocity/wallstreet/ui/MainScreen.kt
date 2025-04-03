package com.velocity.wallstreet.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowUpward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.LocalPlatformContext
import com.velocity.wallstreet.ui.component.AnimatedHeaderText
import com.velocity.wallstreet.ui.component.AppHeader
import com.velocity.wallstreet.ui.component.BottomBarCredits
import com.velocity.wallstreet.ui.component.CategoryButton
import com.velocity.wallstreet.ui.component.GridView
import com.velocity.wallstreet.ui.component.LoadingIndicator
import com.velocity.wallstreet.ui.component.NeoBrutalistButton
import com.velocity.wallstreet.utils.NeoBrutalistShapes
import com.velocity.wallstreet.utils.PlatformUtils
import com.velocity.wallstreet.utils.extractUniqueCategories
import com.velocity.wallstreet.utils.getAppVersion
import com.velocity.wallstreet.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.update_available_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel { MainViewModel() },
    onImageClick: (String) -> Unit
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val collapseFraction by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val gridState = rememberLazyGridState()

    val updateUrl = remember { mutableStateOf("") }

    val categories = extractUniqueCategories(viewState.wallpapers)
    val filteredWallpapers = if (viewState.selectedCategory != null) {
        viewState.wallpapers.filter { it.category == viewState.selectedCategory }
    } else {
        viewState.wallpapers
    }

    val animatedCornerRadius by animateDpAsState(
        targetValue = if (collapseFraction == 1f) 0.dp else NeoBrutalistShapes.Rounded,
        animationSpec = tween(durationMillis = 200)
    )

    LaunchedEffect(viewState.config) {
        updateUrl.value = viewState.config?.let { config ->
            when {
                PlatformUtils.isMacOS() -> config.macUpdateUrl
                PlatformUtils.isWindows() -> config.windowsUpdateUrl
                PlatformUtils.isLinux() -> config.linuxUpdateUrl
                else -> config.androidUpdateUrl
            }
        } ?: ""
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.firstVisibleItemIndex }.collect { index ->
            viewModel.setShowFAB(index > 2)
        }
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
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
                title = {
                    viewState.config?.let { config ->
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
        floatingActionButton = {
            AnimatedVisibility(
                visible = viewState.showFAB,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                NeoBrutalistButton(
                    cornerRadius = NeoBrutalistShapes.Rounded,
                    contentPadding = PaddingValues(18.dp),
                    onClick = {
                        coroutineScope.launch {
                            gridState.animateScrollToItem(0)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.ArrowUpward,
                        contentDescription = "Scroll to top",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
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
                viewState.isLoading -> {
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
                            selectedCategory = viewState.selectedCategory,
                            onCategorySelected = { category ->
                                viewModel.setSelectedCategory(
                                    if (viewState.selectedCategory == category) null else category
                                )
                            }
                        )

                        GridView(
                            wallpapers = filteredWallpapers,
                            onImageClick = onImageClick,
                            gridState = gridState
                        )
                    }
                }
            }
        }
    }
}