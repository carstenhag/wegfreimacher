package de.chagemann.wegfreimacher.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chagemann.wegfreimacher.BrowserLauncher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val browserLauncher: BrowserLauncher,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState(Any()))
    val viewState = _viewState.asStateFlow()

    fun retrieveApiKey(): String? {
        return settingsRepository.wegliApiKey
    }

    fun updateApiKey(apiKeyInput: String) {
        val trimmedInput = apiKeyInput.trim()
        settingsRepository.wegliApiKey = trimmedInput
    }

    fun openUserProfile(context: Context) {
        browserLauncher.openUserProfile(context)
    }

    data class ViewState(
        val x: Any
    )

}
