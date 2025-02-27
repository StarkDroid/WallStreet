package com.velocity.wallstreet.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wallpapers(
    @SerialName("desktop")
    val desktop: List<Model>,
    val mobile: List<Model>
)

@Serializable
data class Model(
    val category: String,
    val imageUrl: String,
)