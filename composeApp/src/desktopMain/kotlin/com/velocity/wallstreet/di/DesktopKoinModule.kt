package com.velocity.wallstreet.di

import com.velocity.wallstreet.data.factory.ContextFactory
import com.velocity.wallstreet.utils.WallpaperFunctions
import com.velocity.wallstreet.utils.WallpaperUtilsDesktop
import com.velocity.wallstreet.viewmodel.MainViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val desktopModule = module {
    singleOf(::WallpaperUtilsDesktop) { bind<WallpaperFunctions>() }
    single { ContextFactory() }
    viewModelOf(::MainViewModel)
}