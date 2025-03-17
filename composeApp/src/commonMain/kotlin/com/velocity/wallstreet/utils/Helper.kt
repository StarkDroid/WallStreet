package com.velocity.wallstreet.utils

object PlatformUtils {
    fun isMacOS(): Boolean = System.getProperty("os.name").contains("Mac")
}

expect fun getAppVersion(context: Any): String