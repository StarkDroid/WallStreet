package com.velocity.wallstreet.data.factory

import platform.Foundation.NSBundle

actual class ContextFactory {
    actual fun getContext(): Any = NSBundle.mainBundle
}