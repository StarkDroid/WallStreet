package com.velocity.wallstreet.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wallpapers(
    @SerialName("config")
    val config: Config,
    @SerialName("desktop")
    val desktop: List<Model>,
    @SerialName("mobile")
    val mobile: List<Model>
)

@Serializable
data class Config(
    val appUpdateVersion: String?,
    val androidUpdateUrl: String?,
    val windowsUpdateUrl: String?,
    val linuxUpdateUrl: String?,
    val macUpdateUrl: String?
)

@Serializable
data class Model(
    val category: String,
    val imageUrl: String,
    val thumbnailUrl: String,
)