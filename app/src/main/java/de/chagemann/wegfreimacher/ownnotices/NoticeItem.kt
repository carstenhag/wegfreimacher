package de.chagemann.wegfreimacher.ownnotices

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import de.chagemann.wegfreimacher.R
import de.chagemann.wegfreimacher.data.NoticeDto
import de.chagemann.wegfreimacher.ui.theme.WegfreimacherTheme

@Composable
fun NoticeItem(
    notice: NoticeDto,
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
            NoticeItemStatusLabel(status = notice.status)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${notice.registration} - ${notice.createdAt}",
            )
        }
    }
}

@Composable
fun NoticeItemStatusLabel(status: NoticeDto.NoticeStatus) {
    Box(
        modifier = Modifier.background(
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

val NoticeDto.NoticeStatus.backgroundColor: Color
    get() = when (this) {
        NoticeDto.NoticeStatus.OPEN -> Color.DarkGray
        NoticeDto.NoticeStatus.DISABLED -> Color.Red
        NoticeDto.NoticeStatus.ANALYZING -> Color.Green
        NoticeDto.NoticeStatus.SHARED -> Color.Blue
    }

@get:StringRes
val NoticeDto.NoticeStatus.textLabelRes: Int
    get() = when (this) {
        NoticeDto.NoticeStatus.OPEN -> R.string.notice_status_open
        NoticeDto.NoticeStatus.DISABLED -> R.string.notice_status_disabled
        NoticeDto.NoticeStatus.ANALYZING -> R.string.notice_status_analyzing
        NoticeDto.NoticeStatus.SHARED -> R.string.notice_status_shared
    }

@Composable
@Preview(showBackground = true)
fun NoticeItemPreview() {
    WegfreimacherTheme {
        val noticeDto = NoticeDto(status = NoticeDto.NoticeStatus.OPEN, registration = "Test")
        NoticeItem(
            notice = noticeDto,
            onItemClicked = {}
        )
    }
}