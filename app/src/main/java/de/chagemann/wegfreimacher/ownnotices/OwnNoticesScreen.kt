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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.chagemann.wegfreimacher.AppPreview
import de.chagemann.wegfreimacher.data.Notice
import de.chagemann.wegfreimacher.ownnotices.OwnNoticesViewModel.ViewState
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

private const val noticeItemContentType = "NoticeItem"

@Composable
fun OwnNoticesScreen(
    viewModel: OwnNoticesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.viewState.collectAsState()
    LaunchedEffect("") {
        viewModel.loadOwnNotices()
    }

    OwnNoticesContent(
        state = state.value,
        onItemTapped = { noticeToken ->
            viewModel.openNotice(context, noticeToken)
        }
    )
}

@Composable
fun OwnNoticesContent(
    state: ViewState,
    onItemTapped: (noticeToken: String) -> Unit
) {
    val noticesState = state.ownNoticesState
    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Box {
            OwnNoticesList(noticesState, onItemTapped)
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isOwnNoticesLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
private fun OwnNoticesList(
    noticesState: ViewState.OwnNoticesState?,
    onItemTapped: (noticeToken: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (noticesState is ViewState.OwnNoticesState.Data) {
            items(
                items = noticesState.notices,
                key = { it.hashCode() },
                contentType = { noticeItemContentType }
            ) { notice ->
                NoticeItem(
                    notice = notice,
                    onItemClicked = { onItemTapped(notice.token) }
                )
            }
        }
    }
}

private class OwnNoticesViewStateProvider : PreviewParameterProvider<ViewState> {
    val dataState = ViewState(
        ownNoticesState = ViewState.OwnNoticesState.Data(Notice.exampleData)
    )
    val loadingState = ViewState(
        isOwnNoticesLoading = true
    )

    override val values: Sequence<ViewState>
        get() = sequenceOf(
            dataState,
            loadingState
        )
}

@AppPreview
@Composable
fun OwnNoticesContentPreview(
    @PreviewParameter(OwnNoticesViewStateProvider::class) viewState: ViewState
) {
    WegfreimacherTheme {
        OwnNoticesContent(state = viewState, onItemTapped = {})
    }
}
