package com.velocity.wallstreet.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.LocalPlatformContext
import com.velocity.wallstreet.ui.component.AppHeader
import com.velocity.wallstreet.ui.component.BottomBarCredits
import com.velocity.wallstreet.ui.component.CategoryButton
import com.velocity.wallstreet.ui.component.FloatingActionButton
import com.velocity.wallstreet.ui.component.GridView
import com.velocity.wallstreet.ui.component.LoadingIndicator
import com.velocity.wallstreet.utils.NeoBrutalistShapes
import com.velocity.wallstreet.utils.extractUniqueCategories
import com.velocity.wallstreet.utils.getAppVersion
import com.velocity.wallstreet.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    onImageClick: (String) -> Unit = {}
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val collapseFraction by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }
    val animatedCornerRadius by animateDpAsState(
        targetValue = if (collapseFraction == 1f) 0.dp else NeoBrutalistShapes.Rounded,
        animationSpec = tween(durationMillis = 200)
    )

    val (categories, filteredWallpapers) = remember(
        viewState.wallpapers,
        viewState.selectedCategory
    ) {
        Pair(
            extractUniqueCategories(viewState.wallpapers),
            if (viewState.selectedCategory != null) {
                viewState.wallpapers.filter { it.category == viewState.selectedCategory }
            } else {
                viewState.wallpapers
            }
        )
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.firstVisibleItemIndex }.collect { index ->
            viewModel.setShowFAB(index > 2)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            viewState.config?.let {
                AppHeader(
                    viewState = viewState,
                    currentAppVersion = getAppVersion(LocalPlatformContext.current),
                    latestAppVersion = it.appUpdateVersion,
                    scrollBehavior = scrollBehavior
                )
            }
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
            FloatingActionButton(
                viewState = viewState,
                scope = coroutineScope,
                gridState = gridState
            )
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
                    LoadingIndicator()
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