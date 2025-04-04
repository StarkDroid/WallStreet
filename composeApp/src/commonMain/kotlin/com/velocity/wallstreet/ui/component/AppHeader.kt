package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.utils.PlatformUtils
import com.velocity.wallstreet.utils.isNewVersionAvailable
import com.velocity.wallstreet.viewmodel.MainScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.main_screen_header_logo
import wallstreet.composeapp.generated.resources.mainscreen_title_text
import wallstreet.composeapp.generated.resources.update_available_text
import wallstreet.composeapp.generated.resources.wallstreet_logo_android
import wallstreet.composeapp.generated.resources.wallstreet_logo_common

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppHeader(
    viewState: MainScreenState,
    currentAppVersion: String,
    latestAppVersion: String,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val isUpdateAvailable = isNewVersionAvailable(currentAppVersion, latestAppVersion)

    val updateUrl = remember { mutableStateOf("") }

    val hyperLinkText = buildAnnotatedString {
        withLink(
            LinkAnnotation.Url(
                url = updateUrl.value,
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

    LaunchedEffect(viewState.config) {
        updateUrl.value = viewState.config?.let { config ->
            when {
                PlatformUtils.isMacOS() -> config.macUpdateUrl
                PlatformUtils.isWindows() -> config.windowsUpdateUrl
                PlatformUtils.isLinux() -> config.linuxUpdateUrl
                else -> config.androidUpdateUrl
            }
        } ?: ""
    }

    LargeTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
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
                        color = MaterialTheme.colorScheme.inversePrimary
                    )

                    if (isUpdateAvailable) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = hyperLinkText,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = currentAppVersion,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
    AnimatedHeaderText(scrollBehavior = scrollBehavior)
}