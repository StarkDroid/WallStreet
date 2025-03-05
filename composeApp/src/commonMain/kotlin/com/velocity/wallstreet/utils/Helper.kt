package com.velocity.wallstreet.utils

import java.util.Locale

object PlatformUtils {
    fun isMacOS(): Boolean = System.getProperty("os.name").contains("Mac")
}