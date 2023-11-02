package de.chagemann.wegfreimacher.data

import de.chagemann.wegfreimacher.settings.SettingsRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

class WegliService @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val publicWegliApi: PublicWegliApi,
    private val privateWegliApi: PrivateWegliApi,
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
            onSuccess = { OwnNoticesResult.Success(it.toPersistentList()) },
            onFailure = { OwnNoticesResult.GenericError }
        )
    }

    sealed class OwnNoticesResult {
        data object NoApiKeySpecifiedError : OwnNoticesResult()
        data object GenericError : OwnNoticesResult()
        data class Success(val ownNotices: PersistentList<NoticeDto>) : OwnNoticesResult()
    }
}