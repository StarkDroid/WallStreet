package com.velocity.wallstreet.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringArrayResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.subtitle_texts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedHeaderText(scrollBehavior: TopAppBarScrollBehavior) {

    val headerTexts = stringArrayResource(Res.array.subtitle_texts)

    var currentSubtitleIndex by remember { mutableStateOf(0) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(headerTexts.size) {
        coroutineScope.launch {
            while (true) {
                delay(3000)
                currentSubtitleIndex = (currentSubtitleIndex + 1) % headerTexts.size
            }
        }
    }

    val isVisible = remember { derivedStateOf { scrollBehavior.state.collapsedFraction < 0.5f } }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = TopAppBarDefaults.TopAppBarExpandedHeight / 2),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = currentSubtitleIndex,
                transitionSpec = {
                    (slideInVertically { it } + fadeIn()) togetherWith
                            (slideOutVertically { it } + fadeOut())
                }
            ) { index ->
                Text(
                    text = headerTexts[index],
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}