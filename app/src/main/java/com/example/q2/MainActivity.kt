package com.example.q2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.example.q2.ui.theme.Q2Theme


// This is the main starting point for the app's UI
class MainActivity : ComponentActivity() {

    private val vm: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Q2Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    var screen by remember { mutableStateOf("counter") }
                    val state by vm.state.collectAsState()

                    when (screen) {
                        "counter" -> CounterScreen(
                            state = state,
                            onInc = vm::increment,
                            onDec = vm::decrement,
                            onReset = vm::reset,
                            onToggleAuto = vm::toggleAuto,
                            onOpenSettings = { screen = "settings" }
                        )
                        "settings" -> SettingsScreen(
                            state = state,
                            onBack = { screen = "counter" },
                            onSetIntervalMs = vm::setIntervalMillis
                        )
                    }
                }
            }
        }
    }
}
