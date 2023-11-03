package de.chagemann.wegfreimacher.data

data class Notice(
    val token: String,
    val registration: String?,
    val createdAt: String,
    val sentAt: String?,
    val status: Status
) {
    enum class Status {
        Open,
        Disabled,
        Analyzing,
        Shared
    }
}
