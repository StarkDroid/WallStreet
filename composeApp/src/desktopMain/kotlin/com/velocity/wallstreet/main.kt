package com.velocity.wallstreet

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.launcher


fun main() = application {
    val icon = painterResource(resource = Res.drawable.launcher)

    Window(
        onCloseRequest = ::exitApplication,
        title = "WallStreet",
        icon = icon
    ) {
        App()
    }
}