package com.velocity.wallstreet.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp,
            color = Color.Black
        ),

        bodyLarge = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Bold,
            fontSize = AppTypography.bodyLarge,
            color = Color.Black
        ),

        bodyMedium = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black,
        ),

        bodySmall = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black
        ),

        labelSmall = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Black
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