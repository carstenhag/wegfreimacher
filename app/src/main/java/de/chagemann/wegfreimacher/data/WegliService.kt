package de.chagemann.wegfreimacher.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import de.chagemann.wegfreimacher.FormattingUtils
import de.chagemann.wegfreimacher.selectimages.ImageUploadContainerDto
import de.chagemann.wegfreimacher.selectimages.ImageUploadDto
import de.chagemann.wegfreimacher.selectimages.md5Hash
import de.chagemann.wegfreimacher.settings.SettingsRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class WegliService @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val publicWegliApi: PublicWegliApi,
    private val privateWegliApi: PrivateWegliApi,
    private val formattingUtils: FormattingUtils,
) : IWegliService {

    override suspend fun getAllCharges(): List<ChargeDto>? {
        val result = runCatching {
            publicWegliApi.getCharges()
        }
        return result.getOrNull()
    }

    override suspend fun getOwnNotices(): OwnNoticesResult {
        val apiKey = settingsRepository.wegliApiKey
        if (apiKey.isNullOrBlank()) {
            return OwnNoticesResult.NoApiKeySpecifiedError
        }

        val result = runCatching {
            privateWegliApi.getNotices(apiKey)
        }
        return result.fold(
            onSuccess = { noticeDtoList ->
                val noticeList = noticeDtoList.mapNotNull {
                    it.toBusinessObject(formattingUtils)
                }.toPersistentList()
                OwnNoticesResult.Success(noticeList)
            },
            onFailure = { OwnNoticesResult.GenericError }
        )
    }

    override suspend fun uploadImages(
        contentResolver: ContentResolver,
        uriList: List<Uri>,
    ) {
        val apiKey = settingsRepository.wegliApiKey ?: throw Exception() // TODO: Improve this

        val imageUploadDtoList = buildImageUploadDtoList(uriList, contentResolver)

        // Step 1: Request Image Upload URL
        val result = runCatching {
            privateWegliApi.requestImageUploadUrl(
                apiKey,
                ImageUploadContainerDto(imageUploadDtoList[0])
            )
        }
        val response = result.getOrNull()
            ?: throw Exception("failed to request upload URL")

        val mediaType = imageUploadDtoList[0].mimeType?.toMediaType()
        val byteArray = contentResolver.openInputStream(uriList[0]).use { inputStream ->
            inputStream?.readBytes()
        } ?: throw Exception("failed to read data from uri")

        // Step 2: Upload image
        val uploadImageResponse = privateWegliApi.uploadImage(
            response.directUpload.url,
            response.directUpload.headers,
            byteArray.toRequestBody(mediaType, 0, byteArray.size)
        )
        val errorBody = uploadImageResponse.errorBody() // results in 403
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun buildImageUploadDtoList(
        uriList: List<Uri>,
        contentResolver: ContentResolver
    ) = uriList.mapNotNull { uri ->
        val fileSizeNamePair = contentResolver.query(
            uri, null, null, null, null
        )?.let {
            it.moveToFirst()

            val fileNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val fileName = it.getString(fileNameIndex)

            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            val fileSize = it.getLong(sizeIndex)

            it.close()
            fileName to fileSize
        }
        if (fileSizeNamePair == null) return@mapNotNull null

        val fileName = fileSizeNamePair.first
        val sizeInBytes = fileSizeNamePair.second

        val byteArray = contentResolver.openInputStream(uri).use { inputStream ->
            inputStream?.readBytes()
        } ?: return@mapNotNull null

        val md5HashBase64 = Base64.encode(md5Hash(byteArray))
        val contentType = contentResolver.getType(uri)
        ImageUploadDto(fileName, sizeInBytes, md5HashBase64, contentType)
    }

    sealed class OwnNoticesResult {
        data object NoApiKeySpecifiedError : OwnNoticesResult()
        data object GenericError : OwnNoticesResult()
        data class Success(val ownNotices: PersistentList<Notice>) : OwnNoticesResult()
    }
}
