package com.velocity.wallstreet.data.remote

import com.velocity.wallstreet.data.model.Wallpapers
import com.velocity.wallstreet.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class WallpaperApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(HttpRequestRetry) {
            maxRetries = Constants.DEFAULT_RETRY_COUNT
            retryOnServerErrors(maxRetries)
            retryOnException(maxRetries)
        }

        defaultRequest {
            header(HttpHeaders.Accept, "application/json")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        expectSuccess = true
    }

    suspend fun getWallpapers(): Wallpapers {
        val url = Constants.WALLPAPER_API
        return client.get(url).body()
    }

    fun close() {
        client.close()
    }
}