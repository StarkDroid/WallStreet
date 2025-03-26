package com.velocity.wallstreet.utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

class NotificationPermissionHandler(private val context: Context) {
    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    fun setRequestPermissionLauncher(launcher: ActivityResultLauncher<String>) {
        requestPermissionLauncher = launcher
    }

    fun askNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher?.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            Toast.makeText(context, "Notifications permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context,
                "We can't show notifications without the permissions",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}