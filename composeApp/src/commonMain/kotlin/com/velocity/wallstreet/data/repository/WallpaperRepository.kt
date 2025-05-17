package com.velocity.wallstreet.data.repository

import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.model.Wallpapers
import com.velocity.wallstreet.data.remote.WallpaperApiClient
import com.velocity.wallstreet.utils.PlatformUtils
import com.velocity.wallstreet.utils.WallpaperFunctions
import com.velocity.wallstreet.utils.WallpaperType
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException

interface WallpaperRepository {
    suspend fun getWallpaper(): Wallpapers
    suspend fun setWallpaper(imageUrl: String, type: WallpaperType): Result<Unit>
    suspend fun downloadWallpaper(imageUrl: String): Result<String>
    fun getWallpaperList(wallpaperData: Wallpapers): List<Model>
}

class WallpaperRepositoryImpl(
    private val apiClient: WallpaperApiClient,
    private val wallpaperFunctions: WallpaperFunctions
) : WallpaperRepository {

    override suspend fun getWallpaper(): Wallpapers {
        try {
            return apiClient.getWallpapers()
        } catch (e: Exception) {
            println("Error fetching wallpapers: ${e.message}")
            throw when (e) {
                is ClientRequestException -> e
                is ServerResponseException -> e
                else -> Exception("Failed to fetch wallpapers", e)
            }
        }
    }

    override suspend fun setWallpaper(imageUrl: String, type: WallpaperType): Result<Unit> {
        return wallpaperFunctions.setWallpaper(imageUrl = imageUrl, type = type)
    }

    override suspend fun downloadWallpaper(imageUrl: String): Result<String> {
        return wallpaperFunctions.downloadImage(imageUrl = imageUrl)
    }

    override fun getWallpaperList(wallpaperData: Wallpapers): List<Model> {
        return when {
            PlatformUtils.isLinux()
                    || PlatformUtils.isWindows()
                    || PlatformUtils.isMacOS() -> wallpaperData.desktop
            else -> wallpaperData.mobile
        }
    }

}