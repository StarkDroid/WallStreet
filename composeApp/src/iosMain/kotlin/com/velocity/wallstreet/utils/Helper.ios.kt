package com.velocity.wallstreet.utils

import com.velocity.wallstreet.data.factory.ContextFactory
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

actual fun getAppVersion(context: ContextFactory): String {
    val bundle = context.getContext() as NSBundle
    println("Info.plist dictionary: ${bundle.infoDictionary}")
    return bundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "Unknown"
}

actual val httpClientEngine: HttpClientEngineFactory<*> = Darwin