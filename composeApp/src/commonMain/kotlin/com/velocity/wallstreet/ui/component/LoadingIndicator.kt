package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        strokeWidth = 8.dp,
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.primary,
    )
}