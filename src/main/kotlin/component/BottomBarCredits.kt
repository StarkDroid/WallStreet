package component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import utils.handCursor

@Composable
fun BottomBarCredits() {
    val uriHandler = LocalUriHandler.current

    val craftedText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
            append("Crafted with ❤ by Trishiraj, visit source at \uD83D\uDC19")
        }
        pushStringAnnotation("URL", "https://fitgirl-repacks.site/")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Github")
        }
        pop()
    }

    val onClick: (Int) -> Unit = {
        when (it) {
            in craftedText.indexOf("Github") until craftedText.indexOf("Github") + "Github".length -> {
                uriHandler.openUri("https://github.com/StarkDroid/")
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(
            text = craftedText,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(horizontal = 16.dp).pointerHoverIcon(handCursor()),
            onClick = { offset ->
                onClick(offset)
            }
        )
    }
}