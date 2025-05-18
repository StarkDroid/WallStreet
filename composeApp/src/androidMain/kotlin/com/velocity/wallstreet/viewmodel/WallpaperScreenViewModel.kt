package com.velocity.wallstreet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velocity.wallstreet.data.repository.WallpaperRepository
import com.velocity.wallstreet.utils.WallpaperType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WallpaperScreenViewModel(
    private val repository: WallpaperRepository,
    val imageUrl: String
) : ViewModel() {

    private val _isLoading = MutableStateFlow<OperationResult?>(null)
    val isLoading: StateFlow<OperationResult?> = _isLoading.asStateFlow()

    fun applyWallpaper(type: WallpaperType) {
        viewModelScope.launch {
            _isLoading.value = OperationResult.Loading

            val result = repository.setWallpaper(imageUrl, type)

            result.onSuccess {
                _isLoading.value = OperationResult.Success(getSuccessMessage(type))
                delay(2000)
                _isLoading.value = null
            }.onFailure { error ->
                _isLoading.value = OperationResult.Failure(error.message ?: "Unknown error")
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

sealed class OperationResult {
    object Loading : OperationResult()
    data class Success(val message: String) : OperationResult()
    data class Failure(val message: String) : OperationResult()
}