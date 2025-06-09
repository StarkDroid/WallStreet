package com.velocity.wallstreet.data.model

data class MainScreenState(
    val wallpapers: List<Model> = emptyList(),
    val config: Config? = null,
    val isLoading: Boolean = false,
    val selectedCategory: String? = null,
    var showFAB: Boolean = true,
    val error: String? = null,
    val isOnline: Boolean = true
)
