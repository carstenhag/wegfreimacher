package de.chagemann.wegfreimacher.selectimages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectImagesScreen(
    viewModel: SelectImagesViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
    }
}
