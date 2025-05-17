package com.velocity.wallstreet.viewmodel

import androidx.lifecycle.ViewModel
import com.velocity.wallstreet.data.repository.WallpaperRepository
import androidx.lifecycle.viewModelScope
import com.velocity.wallstreet.utils.WallpaperType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WallpaperScreenViewModel(
    private val repository: WallpaperRepository,
    val imageUrl: String
) : ViewModel() {

    private val _state = MutableStateFlow(WallpaperViewState())
    val state = _state.asStateFlow()

    fun applyWallpaper(type: WallpaperType) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = repository.setWallpaper(imageUrl, type)

            result.onSuccess {
                _state.update { it.copy(
                    isLoading = false,
                    actionMessage = getSuccessMessage(type),
                    error = null
                ) }
            }.onFailure { error ->
                _state.update { it.copy(
                    isLoading = false,
                    error = error.message
                ) }
            }
        }
    }

    private fun getSuccessMessage(type: WallpaperType): String {
        return when (type) {
            is WallpaperType.HomeScreen -> "Wallpaper set on home screen"
            is WallpaperType.LockScreen -> "Wallpaper set on lock screen"
            is WallpaperType.Both -> "Wallpaper set on both screens"
            is WallpaperType.DownloadOnly -> "Wallpaper saved to gallery"
        }
    }
}

data class WallpaperViewState(
    val isLoading: Boolean = false,
    val actionMessage: String? = null,
    val error: String? = null
)