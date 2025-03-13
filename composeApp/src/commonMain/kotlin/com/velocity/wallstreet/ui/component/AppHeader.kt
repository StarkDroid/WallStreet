package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.utils.PlatformUtils
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.main_screen_header_logo
import wallstreet.composeapp.generated.resources.mainscreen_title_text
import wallstreet.composeapp.generated.resources.wallstreet_logo_android
import wallstreet.composeapp.generated.resources.wallstreet_logo_common

@Composable
fun AppHeader() {
    Column {
        Row (
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

            Text(
                text = stringResource(Res.string.mainscreen_title_text),
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        Text(
            text = "v".plus(System.getProperty("app.version")),
            style = MaterialTheme.typography.labelSmall
        )
    }
}