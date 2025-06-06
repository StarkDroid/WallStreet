package com.velocity.wallstreet.di

import com.velocity.wallstreet.utils.WallpaperFunctions
import com.velocity.wallstreet.utils.WallpaperUtilsAndroid
import com.velocity.wallstreet.viewmodel.WallpaperScreenViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    singleOf(::WallpaperUtilsAndroid) { bind<WallpaperFunctions>() }

    viewModel { (imageUrl: String) ->
        WallpaperScreenViewModel(
            repository = get(),
            imageUrl = imageUrl
        )
    }
}