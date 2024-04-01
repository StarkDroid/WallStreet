package data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

object WallpaperApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryOnServerErrors(maxRetries)
        }

        defaultRequest {
            header(HttpHeaders.Accept, "application/json")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        expectSuccess = true
    }

    suspend fun getWallpapers(): Wallpapers {
        val url = "https://raw.githubusercontent.com/StarkDroid/WallStreet-API/main/wallstreet_walls.json"
        val response: HttpResponse = client.get(url)
        println(response.body<String>())
        return if (response.status.isSuccess()) {
            decodeFromString(response.body())
        } else {
            throw RuntimeException("Failed to get wallpapers: ${response.status.value}")
        }
    }
}