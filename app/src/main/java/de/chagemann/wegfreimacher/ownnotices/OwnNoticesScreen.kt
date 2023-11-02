package de.chagemann.wegfreimacher.ownnotices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.chagemann.wegfreimacher.MainViewModel

@Composable
fun OwnNoticesScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState()
    val noticesState = state.value.ownNoticesState
    LaunchedEffect(key1 = "") {
        viewModel.loadOwnNotices()
    }

    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (noticesState is MainViewModel.ViewState.OwnNoticesState.Data) {
                    items(
                        items = noticesState.notices,
                        key = { it.hashCode() },
                        contentType = { "Notice Item" }
                    ) { notice ->
                        NoticeItem(notice)
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.value.isOwnNoticesLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
