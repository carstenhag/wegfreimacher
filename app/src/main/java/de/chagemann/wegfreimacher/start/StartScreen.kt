package de.chagemann.wegfreimacher.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StartScreen(
    onOpenOwnNoticesClicked: () -> Unit,
    onOpenSettingsClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = onOpenOwnNoticesClicked) {
            Text("Own notices")
        }
        Button(onClick = onOpenSettingsClicked) {
            Text("Settings")
        }
    }
}
