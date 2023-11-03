package de.chagemann.wegfreimacher

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.chagemann.wegfreimacher.ownnotices.OwnNoticesScreen
import de.chagemann.wegfreimacher.selectimages.SelectImagesScreen
import de.chagemann.wegfreimacher.settings.SettingsScreen
import de.chagemann.wegfreimacher.start.StartScreen
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pickMediaLauncher = registerForActivityResult(PickMultipleVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WegfreimacherTheme {
                ScreenNavHost(pickMediaLauncher)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenNavHost(
    pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>
) {
    Scaffold { innerPadding ->
        val navController = rememberNavController()
        NavHost(
            navController,
            startDestination = Screen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Start.name) {
                StartScreen(
                    onOpenOwnNoticesClicked = { navController.navigate(route = Screen.OwnNotices.name) },
                    onOpenSettingsClicked = { navController.navigate(route = Screen.Settings.name) },
                    onOpenSelectImagesClicked = { navController.navigate(route = Screen.SelectImages.name) },
                )
            }

            composable(route = Screen.OwnNotices.name) {
                OwnNoticesScreen()
            }

            composable(route = Screen.Settings.name) {
                SettingsScreen()
            }

            composable(route = Screen.SelectImages.name) {
                SelectImagesScreen(
                    onSelectImagesClicked = {
                        // Launch the photo picker and let the user choose only images.
                        pickMediaLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))
                    }
                )
            }
        }
    }
}

enum class Screen {
    Start,
    OwnNotices,
    Settings,
    SelectImages,
}
