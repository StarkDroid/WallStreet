package com.velocity.wallstreet

import android.app.Application
import com.velocity.wallstreet.di.androidModule
import com.velocity.wallstreet.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class WallStreetApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@WallStreetApplication)
            androidLogger(Level.DEBUG)
            modules(androidModule)
        }
    }
}