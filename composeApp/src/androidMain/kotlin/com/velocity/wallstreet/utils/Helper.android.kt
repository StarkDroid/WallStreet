package com.velocity.wallstreet.utils

import android.content.Context

actual fun getAppVersion(context: Any): String {
    val androidContext = context as? Context ?: return "Unknown"
    val packageInfo = androidContext.packageManager.getPackageInfo(androidContext.packageName, 0)
    return packageInfo.versionName ?: "Unknown"
}