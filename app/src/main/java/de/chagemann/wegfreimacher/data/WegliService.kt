package de.chagemann.wegfreimacher.data

import javax.inject.Inject

class WegliService @Inject constructor(
    private val publicWegliApi: PublicWegliApi,
    private val privateWegliApi: PrivateWegliApi,
) : IWegliService {

    override suspend fun getAllCharges(): List<ChargeDto>? {
        val result = runCatching {
            publicWegliApi.getCharges()
        }
        return result.getOrNull()
    }

    override suspend fun getOwnNotices(): List<NoticeDto>? {
        val apiKey = ""
        val result = runCatching {
            privateWegliApi.getNotices(apiKey)
        }
        return result.getOrNull()
    }
}