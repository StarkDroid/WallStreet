package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.utils.PlatformUtils
import com.velocity.wallstreet.viewmodel.MainScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppHeader(
    viewState: MainScreenState,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val hyperLinkText = buildAnnotatedString {
        withLink(
            LinkAnnotation.Url(
                url = viewState.updateURL,
                TextLinkStyles(
                    style = SpanStyle(
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            )
        ) {
            append(stringResource(Res.string.update_available_text))
        }
    }

    LargeTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.size(52.dp),
                    painter = if (PlatformUtils.isMacOS()) {
                        painterResource(Res.drawable.wallstreet_logo_common)
                    } else {
                        painterResource(Res.drawable.wallstreet_logo_android)
                    },
                    contentDescription = stringResource(Res.string.main_screen_header_logo)
                )

                Column {
                    Text(
                        text = stringResource(Res.string.mainscreen_title_text),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    if (viewState.isUpdateAvailable()) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = hyperLinkText,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = viewState.currentAppVersion,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}