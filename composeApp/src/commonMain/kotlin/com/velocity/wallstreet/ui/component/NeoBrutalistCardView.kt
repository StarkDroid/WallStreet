package com.velocity.wallstreet.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.utils.NeoBrutalistShapes

@Composable
fun NeoBrutalistCardView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 2.dp,
    shadowOffset: Dp = 4.dp,
    cornerRadius: Dp = NeoBrutalistShapes.Rounded,
    content: @Composable BoxScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(cornerRadius)
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> isPressed = true
                is HoverInteraction.Exit -> isPressed = false
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release -> isPressed = false
                is PressInteraction.Cancel -> isPressed = false
            }
        }
    }

    val currentOffset by animateDpAsState(
        targetValue = if (isPressed) 0.dp else shadowOffset,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .height(350.dp)
            .padding(8.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            ),
    ) {
        Box(
            modifier = Modifier
                .offset(x = currentOffset, y = currentOffset)
                .fillMaxWidth()
                .border(borderWidth, borderColor, shape)
                .background(borderColor, shape)
                .matchParentSize()
        )

        Box(
            modifier = Modifier
                .border(borderWidth, borderColor, shape)
                .background(backgroundColor, shape)
                .clip(shape),
        ) {
            content()
        }
    }
}