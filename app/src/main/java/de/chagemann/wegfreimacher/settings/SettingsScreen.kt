package de.chagemann.wegfreimacher.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import de.chagemann.wegfreimacher.AppPreview
import de.chagemann.wegfreimacher.R
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState()
    val context = LocalContext.current

    val wegliApiKeyInput = remember { mutableStateOf("") }
    LaunchedEffect("") {
        val apiKey = viewModel.retrieveApiKey() ?: return@LaunchedEffect
        wegliApiKeyInput.value = apiKey
    }
    SettingsContent(
        wegliApiKeyInput,
        onSaveApiKeyClicked = {
            viewModel.updateApiKey(it)
        },
        onOpenUserProfile = { viewModel.openUserProfile(context) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    wegliApiKeyInput: MutableState<String>,
    onSaveApiKeyClicked: (String) -> Unit,
    onOpenUserProfile: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Settings!")

        Text(text = stringResource(R.string.settings_api_key_label))
        TextField(
            value = wegliApiKeyInput.value,
            onValueChange = {
                wegliApiKeyInput.value = it
            }
        )
        Button(
            onClick = { onSaveApiKeyClicked(wegliApiKeyInput.value) }
        ) {
            Text(text = stringResource(R.string.settings_api_key_save))
        }
        Button(
            onClick = onOpenUserProfile
        ) {
            Text(text = stringResource(R.string.settings_api_key_open_profile))
        }
    }
}

@AppPreview
@Composable
fun SettingsContentPreview() {
    WegfreimacherTheme {
        val input = remember { mutableStateOf("") }
        SettingsContent(
            wegliApiKeyInput = input,
            onSaveApiKeyClicked = {},
            onOpenUserProfile = {},
        )
    }
}
