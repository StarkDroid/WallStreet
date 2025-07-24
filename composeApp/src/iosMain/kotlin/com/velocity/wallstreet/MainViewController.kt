package com.velocity.wallstreet

import androidx.compose.ui.window.ComposeUIViewController
import com.velocity.wallstreet.di.initKoin
import com.velocity.wallstreet.di.iosModule

fun MainViewController() = ComposeUIViewController {
    initKoin { modules(iosModule) }
    App()
}