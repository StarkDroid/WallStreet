package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.utils.Constants

@Composable
fun BottomBarCredits() {
    val uriHandler = LocalUriHandler.current

    val craftedText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
            append(Constants.bottom_bar_credits_text)
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Github")
        }
    }

    val onClick: (Int) -> Unit = { offset ->
        when (offset) {
            in craftedText.indexOf("Github") until craftedText.indexOf("Github") + "Github".length -> {
                uriHandler.openUri(Constants.GITHUB_URL)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

        Text(
            text = craftedText,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .pointerHoverIcon(icon = PointerIcon.Hand)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        textLayoutResult?.let { result ->
                            val position = result.getOffsetForPosition(offset)
                            onClick(position)
                        }
                    }
                },
            onTextLayout = { layoutResult ->
                textLayoutResult = layoutResult
            }
        )
    }
}