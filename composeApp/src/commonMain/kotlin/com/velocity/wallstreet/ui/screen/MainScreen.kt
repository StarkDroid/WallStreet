package com.velocity.wallstreet.ui.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.velocity.wallstreet.ui.component.AppHeader
import com.velocity.wallstreet.ui.component.BottomBarCredits
import com.velocity.wallstreet.ui.component.CategoryButton
import com.velocity.wallstreet.ui.component.FloatingActionButton
import com.velocity.wallstreet.ui.component.GridView
import com.velocity.wallstreet.ui.component.LoadingIndicator
import com.velocity.wallstreet.ui.component.NetworkUI
import com.velocity.wallstreet.utils.NeoBrutalistShapes
import com.velocity.wallstreet.viewmodel.MainScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewState: MainScreenState,
    onImageClick: (String) -> Unit = {},
    showFAB: (Boolean) -> Unit,
    setSelectedCategory: (String?) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val collapseFraction by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }
    val animatedCornerRadius by animateDpAsState(
        targetValue = if (collapseFraction == 1f) 0.dp else NeoBrutalistShapes.Rounded,
        animationSpec = tween(durationMillis = 200),
        label = "corner-radius"
    )

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.firstVisibleItemIndex }.collect { index ->
            showFAB(index > 2)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            AppHeader(
                viewState = viewState,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(40.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                windowInsets = WindowInsets(bottom = 0),
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
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
                .fillMaxSize()
                .padding(padding)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(
                        topStart = animatedCornerRadius,
                        topEnd = animatedCornerRadius
                    )
                )
        ) {
            when {
                viewState.isOnline.not() -> {
                    NetworkUI()
                }

                viewState.isLoading -> {
                    LoadingIndicator()
                }

                else -> {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))

                        CategoryButton(
                            categories = viewState.categories,
                            selectedCategory = viewState.selectedCategory,
                            onCategorySelected = { category ->
                                setSelectedCategory(if (viewState.selectedCategory == category) null else category)
                            }
                        )

                        GridView(
                            wallpapers = viewState.filteredWallpapers,
                            onImageClick = onImageClick,
                            gridState = gridState,
                        )
                    }
                }
            }
        }
    }
}