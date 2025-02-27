package com.velocity.wallstreet.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import wallstreet.composeapp.generated.resources.Outfit_Medium
import wallstreet.composeapp.generated.resources.Pacifico
import wallstreet.composeapp.generated.resources.Res

@Composable
fun AppTypography(): Typography{
    val titleFont = FontFamily(
        Font(resource = Res.font.Pacifico, weight = FontWeight.Medium)
    )

    val bodyFont = FontFamily(
        Font(resource = Res.font.Outfit_Medium, weight = FontWeight.Normal)
    )

    return Typography(
        headlineLarge = TextStyle(
            fontFamily = titleFont,
            fontWeight = FontWeight.Medium,
            fontSize = 38.sp
        ),

        bodyLarge = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Normal,
            fontSize = AppTypography.bodyLarge
        ),

        bodyMedium = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),

        labelSmall = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )
}

/**
 * Since we need different font sizes for different platforms, We define
 * actual/expect block for each platform and add the respective font sizes there.
 * */
expect object AppTypography {
    val bodyLarge: TextUnit
}