package de.chagemann.wegfreimacher.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
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

    data class ViewState(
        val x: Any
    )

}
