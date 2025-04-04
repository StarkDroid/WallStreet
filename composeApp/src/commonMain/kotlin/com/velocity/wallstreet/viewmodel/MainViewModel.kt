package com.velocity.wallstreet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velocity.wallstreet.data.WallpaperApiClient
import com.velocity.wallstreet.data.model.Config
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.utils.getWallpaperList
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val wallpaperData = WallpaperApiClient.getWallpapers()
                _state.update {
                    it.copy(
                        wallpapers = getWallpaperList(wallpaperData).shuffled(),
                        config = wallpaperData.config,
                        isLoading = false
                    )
                }
            } catch (e: ClientRequestException) {
                _state.update { it.copy(isLoading = false) }
                println("Error fetching data: ${e.message}")
            }
        }
    }

    fun setSelectedCategory(category: String?) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun setShowFAB(visible: Boolean) {
        _state.update { it.copy(showFAB = visible) }
    }
}

data class MainScreenState(
    val wallpapers: List<Model> = emptyList(),
    val config: Config? = null,
    val isLoading: Boolean = true,
    val selectedCategory: String? = null,
    var showFAB: Boolean = false,
)