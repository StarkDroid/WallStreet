package com.velocity.wallstreet.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryButton(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    Row (
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
    ) {
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = if (category == selectedCategory) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onCategorySelected(category) }
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}