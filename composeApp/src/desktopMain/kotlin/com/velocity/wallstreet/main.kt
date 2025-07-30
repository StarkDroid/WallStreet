package com.velocity.wallstreet

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.velocity.wallstreet.di.commonModule
import com.velocity.wallstreet.di.desktopModule
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import wallstreet.composeapp.generated.resources.Res
import wallstreet.composeapp.generated.resources.launcher

fun main() {

    startKoin { modules(commonModule + desktopModule) }

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
