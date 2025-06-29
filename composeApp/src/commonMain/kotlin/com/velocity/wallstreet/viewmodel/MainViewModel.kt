package com.velocity.wallstreet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velocity.wallstreet.data.model.MainScreenState
import com.velocity.wallstreet.data.repository.WallpaperRepository
import com.velocity.wallstreet.utils.NetworkMonitor
import com.velocity.wallstreet.utils.PlatformUtils
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel(
    private val repository: WallpaperRepository
) : ViewModel(), KoinComponent {

    private val networkMonitor: NetworkMonitor by inject()

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        checkNetworkAvailability()
        loadData()
    }

    private fun checkNetworkAvailability() {
        viewModelScope.launch {
            if (PlatformUtils.isAndroid()) {
                networkMonitor.isConnected.collect { connected ->
                    _state.update { it.copy(isOnline = connected) }
                }
            }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val wallpaperData = repository.getWallpaper()
                _state.update {
                    it.copy(
                        wallpapers = repository.getWallpaperList(wallpaperData).shuffled(),
                        config = wallpaperData.config,
                        isLoading = false
                    )
                }
            } catch (e: ClientRequestException) {
                _state.update { it.copy(isLoading = false, error = e.message) }
                println("Error fetching data: ${e.message}")
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Failed tto load wallpapers") }
                println("Unexpected error: ${e.message}")
            }
        }
    }

    fun setSelectedCategory(category: String?) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun setShowFAB(visible: Boolean) {
        _state.update { it.copy(showFAB = visible) }
    }

    override fun onCleared() {
        super.onCleared()
        if (PlatformUtils.isAndroid()) {
            networkMonitor.stopMonitoring()
        }
    }
}