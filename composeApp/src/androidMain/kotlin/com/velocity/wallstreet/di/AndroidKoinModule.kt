package com.velocity.wallstreet.di

import android.content.Context
import com.velocity.wallstreet.data.factory.ContextFactory
import com.velocity.wallstreet.utils.WallpaperFunctions
import com.velocity.wallstreet.utils.WallpaperUtilsAndroid
import com.velocity.wallstreet.viewmodel.MainViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun androidModule(context: Context) = module {
    single { ContextFactory(context) }
    singleOf(::WallpaperUtilsAndroid) { bind<WallpaperFunctions>() }
    viewModelOf(::MainViewModel)
}