package com.velocity.wallstreet.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.velocity.wallstreet.R
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

actual fun getAppVersion(context: Any): String {
    val androidContext = context as? Context ?: return "Unknown"
    val packageInfo = androidContext.packageManager.getPackageInfo(androidContext.packageName, 0)
    return packageInfo.versionName ?: "Unknown"
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = context.getString(R.string.default_notification_channel_id)
        val channelName = context.getString(R.string.default_notification_channel_name)
        val channelDescription = "Notifications from WallStreet App"

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}

actual object PlatformUtils {
    actual fun isAndroid(): Boolean = true
    actual fun isIOS(): Boolean = false
    actual fun isLinux(): Boolean = false
    actual fun isWindows(): Boolean = false
    actual fun isMacOS(): Boolean = false
}

actual val httpClientEngine: HttpClientEngineFactory<*> = CIO