package com.example.q2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// This makes the state predictable and easy to manage
data class CounterState(
    val count: Int = 0,
    val auto: Boolean = false,
    val intervalMillis: Long = 3000L // default 3s
)

// This helps data survive configuration changes and keeps the UI code clean.
class CounterViewModel : ViewModel() {

    private val _state = MutableStateFlow(CounterState())
    val state: StateFlow<CounterState> = _state

    private var autoJob: Job? = null

    fun increment() {
        _state.value = _state.value.copy(count = _state.value.count + 1)
    }

    fun decrement() {
        _state.value = _state.value.copy(count = _state.value.count - 1)
    }

    fun reset() {
        _state.value = _state.value.copy(count = 0)
    }

    fun toggleAuto() {
        val newAuto = !_state.value.auto
        _state.value = _state.value.copy(auto = newAuto)
        handleAutoJob(newAuto, _state.value.intervalMillis)
    }

    fun setIntervalMillis(newInterval: Long) {
        // Guard against silly values
        val safe = newInterval.coerceIn(250L, 60_000L)
        _state.value = _state.value.copy(intervalMillis = safe)
        // If auto is ON, restart the job with the new interval
        if (_state.value.auto) {
            handleAutoJob(true, safe)
        }
    }

    private fun handleAutoJob(shouldRun: Boolean, interval: Long) {
        autoJob?.cancel()
        if (!shouldRun) return

        autoJob = viewModelScope.launch {
            while (true) {
                delay(interval)
                // increment from inside the VM to preserve UDF
                _state.value = _state.value.copy(count = _state.value.count + 1)
            }
        }
    }

    // We use it to clean up resources, like stopping our coroutine, when the user navigates away and the ViewModel is destroyed.
    override fun onCleared() {
        autoJob?.cancel()
        super.onCleared()
    }
}