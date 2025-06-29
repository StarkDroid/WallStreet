package com.velocity.wallstreet.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.staticCompositionLocalOf

object PlatformUtils {
    fun isMacOS(): Boolean = System.getProperty("os.name").contains("Mac")
    fun isLinux(): Boolean = System.getProperty("os.name").contains("Linux") && !isAndroid()
    fun isWindows(): Boolean = System.getProperty("os.name").contains("Windows")
    fun isAndroid(): Boolean {
        return try {
            Class.forName("android.os.Build")
            true
        } catch (_: ClassNotFoundException) {
            false
        }
    }

}

expect fun getAppVersion(context: Any): String

fun parseVersion(version: String): List<Int> {
    return version.split(".").map { it.toInt() }
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

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope> {
    error("SharedTransitionScope not provided")
}

val LocalAnimatedVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope> {
    error("AnimatedVisibilityScope not provided")
}