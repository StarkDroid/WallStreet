package com.velocity.wallstreet.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wallpapers(
    @SerialName("desktop")
    val desktop: List<Desktop>
)

@Serializable
data class Desktop(
    val category: String,
    val imageUrl: String,
)