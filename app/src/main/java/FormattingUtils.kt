package de.chagemann.wegfreimacher

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

class FormattingUtils @Inject constructor() {
    private val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    fun formatDayMonthYear(zonedDateTime: ZonedDateTime): String {
        return dateFormatter.format(zonedDateTime)
    }
}
