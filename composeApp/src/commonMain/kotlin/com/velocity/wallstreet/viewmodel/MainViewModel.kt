package com.velocity.wallstreet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velocity.wallstreet.data.factory.ContextFactory
import com.velocity.wallstreet.data.model.Config
import com.velocity.wallstreet.data.model.Model
import com.velocity.wallstreet.data.repository.WallpaperRepository
import com.velocity.wallstreet.utils.NetworkMonitor
import com.velocity.wallstreet.utils.PlatformUtils
import com.velocity.wallstreet.utils.extractUniqueCategories
import com.velocity.wallstreet.utils.getAppVersion
import com.velocity.wallstreet.utils.isNewVersionAvailable
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel(
    private val repository: WallpaperRepository,
    private val context: ContextFactory
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
                val wallpapers = repository.getWallpaperList(wallpaperData)
                val currentVersion = getAppVersion(context)
                val latestVersion = wallpaperData.config.appUpdateVersion
                val updateUrl = when {
                    PlatformUtils.isMacOS() -> wallpaperData.config.macUpdateUrl
                    PlatformUtils.isWindows() -> wallpaperData.config.windowsUpdateUrl
                    PlatformUtils.isLinux() -> wallpaperData.config.linuxUpdateUrl
                    else -> wallpaperData.config.androidUpdateUrl
                }
                val categories = extractUniqueCategories(repository.getWallpaperList(wallpaperData))

                _state.update {
                    it.copy(
                        wallpapers = wallpapers.shuffled(),
                        categories = categories,
                        filteredWallpapers = filteredWallpapers(wallpapers, it.selectedCategory),
                        config = wallpaperData.config,
                        isLoading = false,
                        currentAppVersion = currentVersion,
                        latestAppVersion = latestVersion,
                        updateURL = updateUrl,
                    )
                }
            } catch (e: ClientRequestException) {
                _state.update { it.copy(isLoading = false, error = e.message) }
                println("Error fetching data: ${e.message}")
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Failed to load wallpapers") }
                println("Unexpected error: ${e.message}")
            }
        }
    }

    fun setSelectedCategory(category: String?) {
        _state.update {
            it.copy(
                selectedCategory = category,
                filteredWallpapers = filteredWallpapers(it.wallpapers, category)
            )
        }
    }

    fun setShowFAB(visible: Boolean) {
        _state.update { it.copy(showFAB = visible) }
    }

    private fun filteredWallpapers(
        wallpapers: List<Model>,
        selectedCategory: String?
    ): List<Model> {
        return if (selectedCategory != null) {
            wallpapers.filter { it.category == selectedCategory }
        } else {
            wallpapers
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (PlatformUtils.isAndroid()) {
            networkMonitor.stopMonitoring()
        }
    }
}

data class MainScreenState(
    val wallpapers: List<Model> = emptyList(),
    val filteredWallpapers: List<Model> = emptyList(),
    val categories: List<String> = emptyList(),
    val config: Config? = null,
    val isLoading: Boolean = false,
    val selectedCategory: String? = null,
    var showFAB: Boolean = true,
    val error: String? = null,
    val isOnline: Boolean = true,
    val currentAppVersion: String = "",
    val latestAppVersion: String = "",
    val updateURL: String = ""
) {
    fun isUpdateAvailable(): Boolean {
        return isNewVersionAvailable(currentAppVersion, latestAppVersion)
    }
}