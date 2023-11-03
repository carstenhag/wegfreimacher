package de.chagemann.wegfreimacher.data

import kotlinx.collections.immutable.persistentListOf

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

    companion object {
        val exampleData = persistentListOf(
            Notice(
                token = "123",
                registration = "M DE 1234",
                createdAt = "14.10.23",
                sentAt = null,
                status = Status.Open,
            ),
            Notice(
                token = "123",
                registration = "M DE 1234",
                createdAt = "14.10.23",
                sentAt = null,
                status = Status.Analyzing,
            ),
            Notice(
                token = "123",
                registration = "M DE 1234",
                createdAt = "14.10.23",
                sentAt = null,
                status = Status.Disabled,
            ),
            Notice(
                token = "123",
                registration = "KFX 231",
                createdAt = "14.10.23",
                sentAt = "18.10.23",
                status = Status.Shared
            )
        )
    }
}
