package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeoBrutalistBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    borderWidth: Dp = 2.dp,
    shadowOffset: Dp = 4.dp,
    cornerRadius: Dp = NeoBrutalistButtonShapes.slightlyRounded,
    content: @Composable ColumnScope.() -> Unit
) {

    val shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)

    ModalBottomSheet(
        containerColor = Color.Transparent,
        shape = RectangleShape,
        tonalElevation = 0.dp,
        onDismissRequest = onDismissRequest,
        dragHandle = null
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = shadowOffset, y = shadowOffset)
                    .fillMaxWidth()
                    .border(borderWidth, Color.Black, shape)
                    .background(Color.Black, shape)
                    .matchParentSize()
            )

            Column(
                modifier = Modifier
                    .border(borderWidth, Color.Black, shape)
                    .background(backgroundColor, shape),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}