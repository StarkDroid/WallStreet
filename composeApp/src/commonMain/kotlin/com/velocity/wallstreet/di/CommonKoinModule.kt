package com.velocity.wallstreet.di

import coil3.PlatformContext
import com.velocity.wallstreet.data.remote.WallpaperApiClient
import com.velocity.wallstreet.data.repository.WallpaperRepository
import com.velocity.wallstreet.data.repository.WallpaperRepositoryImpl
import com.velocity.wallstreet.utils.NetworkMonitor
import com.velocity.wallstreet.viewmodel.MainViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::WallpaperApiClient)
    singleOf(::WallpaperRepositoryImpl) { bind<WallpaperRepository>() }

    single {
        val context: PlatformContext = get()
        NetworkMonitor(context).apply { startMonitoring() }
    }

    viewModelOf(::MainViewModel)
}