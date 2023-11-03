package de.chagemann.wegfreimacher

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import javax.inject.Inject

class FormattingUtils @Inject constructor() {
    private val dayMonthYearFormatter = SimpleDateFormat.getDateInstance()

    fun formatDayMonthYear(zonedDateTime: ZonedDateTime): String {
        return dayMonthYearFormatter.format(zonedDateTime)
    }
}
