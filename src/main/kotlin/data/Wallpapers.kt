package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wallpapers(
    @SerialName("desktop")
    val desktop: List<Desktop>
)

@Serializable
data class Desktop(
    val author: String,
    val imageUrl: String,
    @SerialName("wallpaper_name")
    val wallpaperName: String
)