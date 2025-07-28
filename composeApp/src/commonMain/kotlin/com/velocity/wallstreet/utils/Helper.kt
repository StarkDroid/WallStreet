package com.velocity.wallstreet.utils

import io.ktor.client.engine.HttpClientEngineFactory

expect object PlatformUtils {
    fun isAndroid(): Boolean
    fun isIOS(): Boolean
    fun isLinux(): Boolean
    fun isMacOS(): Boolean
    fun isWindows(): Boolean
}

expect fun getAppVersion(context: Any): String

expect val httpClientEngine: HttpClientEngineFactory<*>

fun parseVersion(version: String): List<Int> {
    return version.split(".").map { it.toIntOrNull() ?: 0 }
}

fun isNewVersionAvailable(currentVersion: String, latestVersion: String): Boolean {
    val current = parseVersion(currentVersion)
    val latest = parseVersion(latestVersion)

    for (i in current.indices) {
        if (latest[i] > current[i]) {
            return true
        } else if (latest[i] < current[i]) {
            return false
        }
    }
    return false
}