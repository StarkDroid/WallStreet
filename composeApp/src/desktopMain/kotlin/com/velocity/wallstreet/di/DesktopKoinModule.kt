package com.velocity.wallstreet.di

import com.velocity.wallstreet.utils.WallpaperFunctions
import com.velocity.wallstreet.utils.WallpaperUtilsDesktop
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val desktopModule = module {
    singleOf(::WallpaperUtilsDesktop) { bind<WallpaperFunctions>() }
}