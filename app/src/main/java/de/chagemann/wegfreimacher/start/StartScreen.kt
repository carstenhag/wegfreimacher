package de.chagemann.wegfreimacher.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.chagemann.wegfreimacher.AppPreview
import de.chagemann.wegfreimacher.R
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@Composable
fun StartScreen(
    onOpenOwnNoticesClicked: () -> Unit,
    onOpenSettingsClicked: () -> Unit,
    onOpenSelectImagesClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onOpenSelectImagesClicked) {
            Text(stringResource(R.string.start_create_invoice))
        }
        Spacer(modifier = Modifier.weight(1f))
        Row{
            Button(onClick = onOpenOwnNoticesClicked) {
                Text(stringResource(R.string.start_open_own_notices))
            }
            Spacer(modifier = Modifier.width(36.dp))
            Button(onClick = onOpenSettingsClicked) {
                Text(stringResource(R.string.start_open_settings))
            }
        }

    }
}

@AppPreview
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
