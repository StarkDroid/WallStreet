package com.velocity.wallstreet.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.velocity.wallstreet.theme.LocalAnimatedVisibilityScope
import com.velocity.wallstreet.theme.LocalSharedTransitionScope
import com.velocity.wallstreet.ui.screen.MainScreen
import com.velocity.wallstreet.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

const val MAIN_SCREEN_ROUTE = "mainScreen"

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainScreen(
    sharedTransitionScope: SharedTransitionScope,
    onImageClick: (String) -> Unit,
) {
    composable(route = MAIN_SCREEN_ROUTE) {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides sharedTransitionScope,
            LocalAnimatedVisibilityScope provides this
        ) {
            val viewModel: MainViewModel = koinViewModel()
            val viewState by viewModel.state.collectAsStateWithLifecycle()

            MainScreen(
                onImageClick = onImageClick,
                viewState = viewState,
                showFAB = viewModel::setShowFAB,
                setSelectedCategory = viewModel::setSelectedCategory
            )
        }
    }
}