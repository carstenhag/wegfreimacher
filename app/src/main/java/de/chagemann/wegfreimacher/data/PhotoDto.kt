package de.chagemann.wegfreimacher.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    @SerialName("filename") var filename: String? = null,
    @SerialName("url") var url: String? = null
)
