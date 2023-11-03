package de.chagemann.wegfreimacher.ownnotices

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import de.chagemann.wegfreimacher.R
import de.chagemann.wegfreimacher.data.Notice
import de.chagemann.wegfreimacher.data.Notice.Status
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@Composable
fun NoticeItem(
    notice: Notice,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked()
            },
        colors = CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = "Kennzeichen: ${notice.registration}",
                    )
                    Text(
                        text = "Erstellt: ${notice.createdAt}",
                    )
                    notice.sentAt?.let {
                        Text(
                            text = "Übermittelt: $it",
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                NoticeItemStatusLabel(
                    status = notice.status,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun NoticeItemStatusLabel(
    status: Status,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            color = status.backgroundColor,
            shape = MaterialTheme.shapes.extraSmall
        )
    ) {
        Text(
            text = stringResource(id = status.textLabelRes),
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
    }
}

// TODO: Improve this
val Status.backgroundColor: Color
    get() = when (this) {
        Status.Open -> Color.DarkGray
        Status.Disabled -> Color.Red
        Status.Analyzing -> Color(100, 55, 100)
        Status.Shared -> Color.Blue
    }

@get:StringRes
val Status.textLabelRes: Int
    get() = when (this) {
        Status.Open -> R.string.notice_status_open
        Status.Disabled -> R.string.notice_status_disabled
        Status.Analyzing -> R.string.notice_status_analyzing
        Status.Shared -> R.string.notice_status_shared
    }

private class NoticeItemProvider: PreviewParameterProvider<Notice> {
    private val baseNotice = Notice(
        token = "123",
        registration = "M DE 1234",
        "11-03-2023",
        null,
        status = Status.Open,
    )
    override val values: Sequence<Notice>
        get() = sequenceOf(
            baseNotice,
            baseNotice.copy(
                status = Status.Analyzing
            ),
            baseNotice.copy(
                status = Status.Disabled
            ),
            baseNotice.copy(
                registration = "KFX 231",
                sentAt = "14-04-2023",
                status = Status.Shared
            )
        )
}

@Composable
@Preview(showBackground = true)
fun NoticeItemPreview(
    @PreviewParameter(NoticeItemProvider::class) notice: Notice
) {
    WegfreimacherTheme {
        NoticeItem(
            notice = notice,
            onItemClicked = {}
        )
    }
}
