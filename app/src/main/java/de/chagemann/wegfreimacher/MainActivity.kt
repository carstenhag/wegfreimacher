package de.chagemann.wegfreimacher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WegfreimacherTheme {
                MainScreen()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                //launch { viewModel.loadCharges() }
                launch { viewModel.loadOwnNotices() }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val state = viewModel.viewState.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.background(Color.Gray),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = state.value.ownNotices,
                key = { it.hashCode() },
                contentType = { "Notice Item" }
            ) { notice ->
                NoticeItem(notice)
            }
        }
    }
}

