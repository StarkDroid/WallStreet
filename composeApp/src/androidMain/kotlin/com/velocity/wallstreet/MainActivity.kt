package com.velocity.wallstreet

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowCompat
import com.velocity.wallstreet.navigation.WallStreetNavGraph
import com.velocity.wallstreet.theme.AppTheme
import com.velocity.wallstreet.utils.NotificationPermissionHandler
import com.velocity.wallstreet.utils.createNotificationChannel

class MainActivity : ComponentActivity() {

    private lateinit var notificationPermissionHandler: NotificationPermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        } else {
            enableEdgeToEdge()
        }

        notificationPermissionHandler = NotificationPermissionHandler(this)

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted -> notificationPermissionHandler.handlePermissionResult(isGranted)
            }

        notificationPermissionHandler.setRequestPermissionLauncher(requestPermissionLauncher)

        notificationPermissionHandler.askNotificationPermission()

        createNotificationChannel(this)

        val windowDecor = WindowCompat.getInsetsController(window, window.decorView)

        setContent {
            windowDecor.isAppearanceLightStatusBars = !isSystemInDarkTheme()

            AppTheme {
                WallStreetNavGraph()
            }
        }
    }
}