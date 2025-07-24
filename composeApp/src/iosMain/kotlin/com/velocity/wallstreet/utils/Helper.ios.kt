package com.velocity.wallstreet.utils

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSBundle

actual object PlatformUtils {
    actual fun isAndroid(): Boolean = false
    actual fun isIOS(): Boolean = true
    actual fun isLinux(): Boolean = false
    actual fun isMacOS(): Boolean = false
    actual fun isWindows(): Boolean = false
}

actual fun getAppVersion(context: Any): String {
    val bundle = NSBundle.mainBundle
    return bundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "Unknown"
}

actual val httpClientEngine: HttpClientEngineFactory<*> = Darwin