package de.chagemann.wegfreimacher.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private companion object {
        const val wegliApiKeyPreferenceKey = "wegliApiKeyPreference"
    }

    var wegliApiKey: String?
        get() {
            val x = sharedPreferences.getString(wegliApiKeyPreferenceKey, null)
            return x
        }
        set(value) {
            sharedPreferences.edit {
                putString(wegliApiKeyPreferenceKey, value)
            }
        }
}
