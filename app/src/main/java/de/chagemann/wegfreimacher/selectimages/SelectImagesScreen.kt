package de.chagemann.wegfreimacher.selectimages

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.chagemann.wegfreimacher.AppPreview
import de.chagemann.wegfreimacher.R
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme
import kotlinx.coroutines.launch

@Composable
fun SelectImagesScreen(
    viewModel: SelectImagesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uriList ->
        if (uriList.isEmpty()) return@rememberLauncherForActivityResult

        coroutineScope.launch {
            viewModel.userHasSelectedImages(contentResolver, uriList)
        }
    }

    SelectImagesContent(onSelectImagesClicked = {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    })
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
            Text(text = stringResource(R.string.select_images_select_button))
        }
    }
}

@Composable
private fun NoticeCreationSteps(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
            .background(
                color = Color.LightGray,
                shape = MaterialTheme.shapes.small
            )
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.notice_creation_steps_title),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = stringResource(R.string.notice_creation_steps_1))
        Text(text = stringResource(R.string.notice_creation_steps_2))
        Text(text = stringResource(R.string.notice_creation_steps_3))
    }
}


@AppPreview
@Composable
fun SelectImagesContentPreview() {
    WegfreimacherTheme {
        SelectImagesContent(
            onSelectImagesClicked = {}
        )
    }
}
