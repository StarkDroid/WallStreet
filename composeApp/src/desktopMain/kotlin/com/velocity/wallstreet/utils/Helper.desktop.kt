package com.velocity.wallstreet.utils

actual fun getAppVersion(context: Any): String {
    return "v".plus(System.getProperty("app.version"))
}