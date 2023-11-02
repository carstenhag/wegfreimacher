package de.chagemann.wegfreimacher.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel()
) {
    val state = vm.viewState.collectAsState()
    val wegliApiKeyInput = remember { mutableStateOf("") }
    LaunchedEffect(key1 = "") {
        val apiKey = vm.retrieveApiKey() ?: return@LaunchedEffect
        wegliApiKeyInput.value = apiKey
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Settings!")

        Text("API Key Input")
        TextField(
            value = wegliApiKeyInput.value,
            onValueChange = {
                wegliApiKeyInput.value = it
            }
        )
        Button(
            onClick = {
                vm.updateApiKey(wegliApiKeyInput.value)
            }
        ) {
            Text(text = "Save API Key")
        }
    }
}
