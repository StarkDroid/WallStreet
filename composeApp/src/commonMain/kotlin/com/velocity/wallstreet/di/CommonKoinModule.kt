package com.velocity.wallstreet.di

import com.velocity.wallstreet.data.remote.WallpaperApiClient
import com.velocity.wallstreet.data.repository.WallpaperRepository
import com.velocity.wallstreet.data.repository.WallpaperRepositoryImpl
import com.velocity.wallstreet.viewmodel.MainViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::WallpaperApiClient)
    singleOf(::WallpaperRepositoryImpl) { bind<WallpaperRepository>() }
    viewModelOf(::MainViewModel)
}