package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.R
import com.velocity.wallstreet.utils.WallpaperType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomSheetContent(
    onApplyWallpaper: (WallpaperType) -> Unit,
) {

    Text(
        text = "Apply wallpaper on",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(top = 16.dp)
    )

    Spacer(modifier = Modifier.height(24.dp))

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WallpaperType.values.forEach { type ->
            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .weight(1f)
                    .size(58.dp),
                onClick = {
                    onApplyWallpaper(type)
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        imageVector = type.iconRes,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = stringResource(R.string.wallpaper_set_button_icon_desc)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(type.label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(48.dp))
}


