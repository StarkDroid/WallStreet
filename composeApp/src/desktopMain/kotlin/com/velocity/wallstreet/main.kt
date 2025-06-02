package com.velocity.wallstreet

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.velocity.wallstreet.di.desktopModule
import com.velocity.wallstreet.di.initKoin
import org.jetbrains.compose.resources.painterResource
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.launcher

fun main() {

    initKoin { modules(desktopModule) }

    application {
        val icon = painterResource(resource = Res.drawable.launcher)

        Window(
            onCloseRequest = ::exitApplication,
            title = "WallStreet",
            icon = icon
        ) {
            App()
        }

    }
}
