package com.example.q2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// This is a reusable UI component for a simple top app bar.
@Composable
private fun SimpleTopBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    navigation: @Composable (() -> Unit)? = null
) {
    Surface(tonalElevation = 3.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (navigation != null) {
                navigation()
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    }
}


// This is the main screen of the app it just shows the state it's given and reports user actions back up.
@Composable
fun CounterScreen(
    state: CounterState,
    onInc: () -> Unit,
    onDec: () -> Unit,
    onReset: () -> Unit,
    onToggleAuto: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Scaffold { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // centers vertically
        ) {
            // Settings button above counter
            TextButton(onClick = onOpenSettings) {
                Text("Settings", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(Modifier.height(24.dp))

            // Counter in the center horizontally
            Text(
                text = "Count: ${state.count}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onInc) { Text("+1") }
                Button(onClick = onDec) { Text("-1") }
                OutlinedButton(onClick = onReset) { Text("Reset") }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Auto mode:")
                Switch(
                    checked = state.auto,
                    onCheckedChange = { onToggleAuto() }
                )
                Text(if (state.auto) "ON" else "OFF")
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Interval: ${state.intervalMillis / 1000.0}s",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// It takes the current state to display the interval and provides callbacks for user actions.
@Composable
fun SettingsScreen(
    state: CounterState,
    onBack: () -> Unit,
    onSetIntervalMs: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Settings",
                navigation = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Auto-increment interval")

            // Minimal numeric input
            var secondsText by remember(state.intervalMillis) {
                mutableStateOf((state.intervalMillis / 1000.0).toString())
            }

            OutlinedTextField(
                value = secondsText,
                onValueChange = { secondsText = it },
                singleLine = true,
                label = { Text("Seconds") }
            )

            Button(onClick = {
                val sec = secondsText.toDoubleOrNull()
                if (sec != null) {
                    onSetIntervalMs((sec * 1000).toLong())
                    onBack()
                }
            }) {
                Text("Save")
            }

        }
    }
}
