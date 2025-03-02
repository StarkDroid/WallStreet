package com.velocity.wallstreet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.velocity.wallstreet.navigation.WallStreetNavGraph
import com.velocity.wallstreet.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()
                WallStreetNavGraph(navController = navController)
            }
        }
    }
}