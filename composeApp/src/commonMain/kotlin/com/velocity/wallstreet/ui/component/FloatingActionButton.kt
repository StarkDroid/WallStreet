package com.velocity.wallstreet.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.utils.NeoBrutalistShapes
import com.velocity.wallstreet.viewmodel.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun FloatingActionButton(
    viewState: MainScreenState,
    scope: CoroutineScope,
    gridState: LazyGridState
) {
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
                scope.launch {
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
}