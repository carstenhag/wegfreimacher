package de.chagemann.wegfreimacher.selectimages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@Composable
fun SelectImagesScreen(
    viewModel: SelectImagesViewModel = hiltViewModel()
) {
    SelectImagesContent()
}

@Composable
fun SelectImagesContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "adadssasd")
    }
}


@Preview(showBackground = true)
@Composable
fun SelectImagesContentPreview() {
    WegfreimacherTheme {
        SelectImagesContent()
    }
}
