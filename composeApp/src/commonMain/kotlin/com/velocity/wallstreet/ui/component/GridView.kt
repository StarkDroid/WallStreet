package com.velocity.wallstreet.ui.component

import androidx.compose.runtime.Composable
import com.velocity.wallstreet.data.model.Desktop

@Composable
expect fun GridView(wallpapers: List<Desktop>)