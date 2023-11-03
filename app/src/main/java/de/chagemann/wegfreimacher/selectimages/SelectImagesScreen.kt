package de.chagemann.wegfreimacher.selectimages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@Composable
fun SelectImagesScreen(
    viewModel: SelectImagesViewModel = hiltViewModel(),
    onSelectImagesClicked: () -> Unit
) {
    SelectImagesContent(onSelectImagesClicked = onSelectImagesClicked)
}

@Composable
fun SelectImagesContent(
    modifier: Modifier = Modifier,
    onSelectImagesClicked: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        NoticeCreationSteps(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(72.dp))
        Button(
            onClick = onSelectImagesClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Select images")
        }
    }
}

@Composable
private fun NoticeCreationSteps(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .background(
                Color.LightGray,
                shape = MaterialTheme.shapes.small
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Schritte zur Erstellung einer Meldung:",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = "1.\t\tFotos auswählen & hochladen")
        Text(text = "2.\t\tTatbestand auswählen, Daten ausfüllen")
        Text(text = "3.\t\tMeldung überprüfen und einreichen")
    }
}


@Preview(showBackground = true)
@Composable
fun SelectImagesContentPreview() {
    WegfreimacherTheme {
        SelectImagesContent(
            onSelectImagesClicked = {}
        )
    }
}
