package com.velocity.wallstreet.utils

import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.network.ConnectivityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NetworkMonitor(private val context: PlatformContext) {

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected

    private var job: Job? = null

    @OptIn(ExperimentalCoilApi::class)
    fun startMonitoring(intervalMs: Long = 5000L) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {
            val checker = ConnectivityChecker(context)
            while (isActive) {
                val isOnline = checker.isOnline()
                _isConnected.emit(isOnline)
                delay(intervalMs)
            }
        }
    }

    fun stopMonitoring() {
        job?.cancel()
    }
}