package com.velocity.wallstreet.utils

actual fun getAppVersion(context: Any): String {
    return (System.getProperty("app.version"))
}