package de.chagemann.wegfreimacher.selectimages

import android.content.ContentResolver
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chagemann.wegfreimacher.data.WegliService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File
import java.math.BigInteger
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
    val md5Hash: String,
    @SerialName("content_type")
    val mimeType: String?,
    @SerialName("metadata")
    val metadata: Map<String, String> = mapOf()
)

fun md5Hash(byteArray: ByteArray): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest(byteArray))
    return String.format("%032x", bigInt)
}

fun File.mimeType(): String? =
    MimeTypeMap.getSingleton().getMimeTypeFromExtension(this.extension)
