package de.chagemann.wegfreimacher.data

interface IWegliService {
    suspend fun getAllCharges(): List<ChargeDto>?
    suspend fun getOwnNotices(): WegliService.OwnNoticesResult
}