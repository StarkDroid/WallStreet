package com.velocity.wallstreet.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.velocity.wallstreet.R
import com.velocity.wallstreet.theme.AppColors.successGreen
import com.velocity.wallstreet.utils.WallpaperType
import com.velocity.wallstreet.viewmodel.OperationResult
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.ic_error
import wallstreet.composeapp.generated.resources.ic_refresh
import wallstreet.composeapp.generated.resources.ic_success
import wallstreet.composeapp.generated.resources.performing_action

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomSheetContent(
    result: OperationResult? = null,
    onApplyWallpaper: (WallpaperType) -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    if (result != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val (icon, tint, needsAnimation) = when (result) {
                is OperationResult.Success -> Triple(
                    vectorResource(Res.drawable.ic_success),
                    successGreen,
                    false
                )

                is OperationResult.Failure -> Triple(
                    vectorResource(Res.drawable.ic_error),
                    MaterialTheme.colorScheme.error,
                    false
                )

                else -> Triple(
                    vectorResource(Res.drawable.ic_refresh),
                    MaterialTheme.colorScheme.primary,
                    true
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .let {
                        if (needsAnimation) {
                            it.rotate(rotation).alpha(scale)
                        } else {
                            it
                        }
                    },
                tint = tint
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (result) {
                    is OperationResult.Success -> result.message
                    is OperationResult.Failure -> result.message
                    else -> stringResource(Res.string.performing_action)
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    } else {

        Spacer(modifier = Modifier.height(48.dp))

        FlowColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WallpaperType.values.forEach { type ->
                NeoBrutalistButton(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    onClick = {
                        onApplyWallpaper(type)
                    },
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = vectorResource(type.iconRes),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentDescription = stringResource(R.string.wallpaper_set_button_icon_desc)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = stringResource(type.label),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(48.dp))
}



