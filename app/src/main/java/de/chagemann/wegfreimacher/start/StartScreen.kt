package de.chagemann.wegfreimacher.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@Composable
fun StartScreen(
    onOpenOwnNoticesClicked: () -> Unit,
    onOpenSettingsClicked: () -> Unit,
    onOpenSelectImagesClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onOpenOwnNoticesClicked) {
            Text("Own notices")
        }
        Button(onClick = onOpenSettingsClicked) {
            Text("Settings")
        }
        Button(onClick = onOpenSelectImagesClicked) {
            Text("Meldung erstellen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    WegfreimacherTheme {
        StartScreen(
            onOpenOwnNoticesClicked = {},
            onOpenSettingsClicked = {},
            onOpenSelectImagesClicked = {}
        )
    }
}
