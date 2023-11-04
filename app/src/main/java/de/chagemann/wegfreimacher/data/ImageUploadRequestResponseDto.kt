package de.chagemann.wegfreimacher.de.chagemann.wegfreimacher.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Response to the request of getting an URL to upload the images to
// TODO: rename this
@Serializable
data class ImageUploadRequestResponseDto(
    @SerialName("direct_upload")
    val directUpload: DirectUploadDto
) {
    @Serializable
    data class DirectUploadDto(
        @SerialName("url")
        val url: String,

        @SerialName("headers")
        val headers: Map<String, String>
    )
}
