package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.desktop_wallpaper_download
import wallstreet.composeapp.generated.resources.ic_apply_wallpaper
import wallstreet.composeapp.generated.resources.ic_download_wallpaper
import wallstreet.composeapp.generated.resources.wallpaper_screen_button_label

@Composable
fun BottomBarContent(
    onToggleBottomSheet: (Boolean) -> Unit,
    onDownloadWallpaper: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {
        NeoBrutalistButton(
            onClick = { onToggleBottomSheet(true) },
            cornerRadius = 8.dp
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_apply_wallpaper),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = stringResource(Res.string.wallpaper_screen_button_label)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(Res.string.wallpaper_screen_button_label),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
            )
        }

        NeoBrutalistButton(
            onClick = onDownloadWallpaper,
            modifier = Modifier.wrapContentWidth(),
            cornerRadius = 8.dp
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_download_wallpaper),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = stringResource(Res.string.desktop_wallpaper_download),
            )
        }
    }
}