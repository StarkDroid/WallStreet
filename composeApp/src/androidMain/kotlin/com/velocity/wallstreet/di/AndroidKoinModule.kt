package com.velocity.wallstreet.di

import com.velocity.wallstreet.utils.WallpaperFunctions
import com.velocity.wallstreet.utils.WallpaperUtilsAndroid
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule = module {
    singleOf(::WallpaperUtilsAndroid) { bind<WallpaperFunctions>() }
}