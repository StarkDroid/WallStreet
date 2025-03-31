package com.velocity.wallstreet.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velocity.wallstreet.data.WallpaperApiClient
import com.velocity.wallstreet.data.model.Config
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.utils.getWallpaperList
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _wallpapers = mutableStateOf<List<Model>>(emptyList())
    val wallpapers: State<List<Model>> = _wallpapers

    private val _config = mutableStateOf<Config?>(null)
    val config: State<Config?> = _config

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        loadWallpapers()
    }

    private fun loadWallpapers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val wallpaperData = WallpaperApiClient.getWallpapers()
                _wallpapers.value = getWallpaperList(wallpaperData).shuffled()
                _config.value = wallpaperData.config
            } catch (e: ClientRequestException) {
                println("Error fetching data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}