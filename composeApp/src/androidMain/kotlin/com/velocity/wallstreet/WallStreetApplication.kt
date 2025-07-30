package com.velocity.wallstreet

import android.app.Application
import com.velocity.wallstreet.di.androidModule
import com.velocity.wallstreet.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WallStreetApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WallStreetApplication)
            modules(commonModule + androidModule(this@WallStreetApplication))
        }
    }
}