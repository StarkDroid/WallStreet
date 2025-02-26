package com.velocity.wallstreet.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringArrayResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.subtitle_texts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedHeaderText(scrollBehavior: TopAppBarScrollBehavior) {

    val headerTexts = stringArrayResource(Res.array.subtitle_texts)

    var currentSubtitleIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentSubtitleIndex = (currentSubtitleIndex + 1) % headerTexts.size
        }
    }

    AnimatedVisibility(
        visible = scrollBehavior.state.collapsedFraction < 0.5f,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = TopAppBarDefaults.TopAppBarExpandedHeight.div(2)),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = currentSubtitleIndex,
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn()) togetherWith
                    (slideOutVertically { height -> -height } + fadeOut())
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