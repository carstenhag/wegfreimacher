package de.chagemann.wegfreimacher.data

import android.content.ContentResolver
import android.net.Uri

interface IWegliService {
    suspend fun getAllCharges(): List<ChargeDto>?
    suspend fun getOwnNotices(): WegliService.OwnNoticesResult
    suspend fun uploadImages(
        contentResolver: ContentResolver,
        uriList: List<Uri>,
    )
}
