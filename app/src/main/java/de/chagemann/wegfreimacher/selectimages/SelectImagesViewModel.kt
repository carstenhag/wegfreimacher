package de.chagemann.wegfreimacher.selectimages

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chagemann.wegfreimacher.data.WegliService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.security.MessageDigest
import javax.inject.Inject


@HiltViewModel
class SelectImagesViewModel @Inject constructor(
    private val wegliService: WegliService,
) : ViewModel() {
    suspend fun userHasSelectedImages(contentResolver: ContentResolver, uriList: List<Uri>) {
        wegliService.uploadImages(contentResolver, uriList)
    }
}

@Serializable
data class ImageUploadContainerDto(
    @SerialName("upload")
    val upload: ImageUploadDto
)

@Serializable
data class ImageUploadDto(
    @SerialName("filename")
    val fileName: String,
    @SerialName("byte_size")
    val sizeInBytes: Long,
    @SerialName("checksum")
    val md5HashBase64: String,
    @SerialName("content_type")
    val mimeType: String?,
    @SerialName("metadata")
    val metadata: Map<String, String> = mapOf()
)

fun md5Hash(byteArray: ByteArray): ByteArray {
    val md = MessageDigest.getInstance("MD5")
    return md.digest(byteArray)
}
