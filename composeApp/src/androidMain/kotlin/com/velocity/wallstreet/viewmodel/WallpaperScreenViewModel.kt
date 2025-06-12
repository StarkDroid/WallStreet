package com.velocity.wallstreet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velocity.wallstreet.data.repository.WallpaperRepository
import com.velocity.wallstreet.utils.WallpaperType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WallpaperScreenViewModel(
    private val repository: WallpaperRepository,
    val imageUrl: String
) : ViewModel() {

    private val _viewState = MutableStateFlow(WallpaperScreenViewState(imageUrl))
    val viewState: StateFlow<WallpaperScreenViewState> = _viewState.asStateFlow()

    fun applyWallpaper(type: WallpaperType) {
        viewModelScope.launch {
            _viewState.update {
                it.copy(applyWallpaperState = OperationResult.Loading)
            }

            val result = repository.setWallpaper(_viewState.value.imageUrl, type)

            result.onSuccess {
                _viewState.update {
                    it.copy(applyWallpaperState = OperationResult.Success(getSuccessMessage(type)))
                }
                delay(2000)
                _viewState.update { it.copy(applyWallpaperState = null) }
            }.onFailure { error ->
                _viewState.update {
                    it.copy(
                        applyWallpaperState = OperationResult.Failure(
                            error.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    fun onImageLoaded() {
        _viewState.update { it.copy(isLoading = false) }
    }

    fun toggleBottomSheet(show: Boolean) {
        _viewState.update { it.copy(showBottomSheet = show) }
    }

    fun downloadWallpaper() {
        viewModelScope.launch {
            repository.downloadWallpaper(imageUrl).onSuccess {
                _viewState.update {
                    it.copy(
                        showBottomSheet = true,
                        applyWallpaperState = OperationResult.Success("Wallpaper saved to gallery")
                    )
                }
                delay(2000)
                _viewState.update {
                    it.copy(
                        applyWallpaperState = null,
                        showBottomSheet = false
                    )
                }
            }
        }
    }

    private fun getSuccessMessage(type: WallpaperType): String {
        return when (type) {
            is WallpaperType.HomeScreen -> "Wallpaper set on home screen"
            is WallpaperType.LockScreen -> "Wallpaper set on lock screen"
            is WallpaperType.Both -> "Wallpaper set on both screens"
        }
    }
}

sealed class OperationResult {
    data object Loading : OperationResult()
    data class Success(val message: String) : OperationResult()
    data class Failure(val message: String) : OperationResult()
}

data class WallpaperScreenViewState(
    val imageUrl: String,
    val isLoading: Boolean = true,
    var showBottomSheet: Boolean = false,
    val applyWallpaperState: OperationResult? = null
)