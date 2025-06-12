package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import wallstreet.composeapp.generated.resources.ic_apply_wallpaper
import wallstreet.composeapp.generated.resources.wallpaper_screen_button_label

@Composable
fun BottomBarContent(
    onToggleBottomSheet: (Boolean) -> Unit,
) {
    NeoBrutalistButton(
        onClick = {
            onToggleBottomSheet(true)
        },
        modifier = Modifier
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        cornerRadius = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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
    }
}