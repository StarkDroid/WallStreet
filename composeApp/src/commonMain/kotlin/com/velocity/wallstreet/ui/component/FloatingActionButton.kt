package com.velocity.wallstreet.ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.data.model.MainScreenState
import com.velocity.wallstreet.utils.NeoBrutalistShapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.ic_scroll_up

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
                imageVector = vectorResource(Res.drawable.ic_scroll_up),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = "Scroll to top"
            )
        }
    }
}