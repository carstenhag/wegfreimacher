package de.chagemann.wegfreimacher

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chagemann.wegfreimacher.data.ChargeDto
import de.chagemann.wegfreimacher.data.IWegliService
import de.chagemann.wegfreimacher.data.NoticeDto
import de.chagemann.wegfreimacher.data.WegliService
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val wegliService: IWegliService
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    data class ViewState(
        val availableCharges: ImmutableList<ChargeDto> = persistentListOf(),
        val ownNoticesState: OwnNoticesState? = null,
        val isOwnNoticesLoading: Boolean = false,
    ) {
        sealed class OwnNoticesState {
            data class Data(val notices: PersistentList<NoticeDto>): OwnNoticesState()
        }
    }

    suspend fun loadOwnNotices() {
        _viewState.update { it.copy(isOwnNoticesLoading = true) }
        val notices = wegliService.getOwnNotices()
        _viewState.update { it.copy(isOwnNoticesLoading = false) }

        when (notices) {
            is WegliService.OwnNoticesResult.GenericError -> {
                // todo: trigger error event
                println("WegliService.OwnNoticesResult.GenericError")
            }
            is WegliService.OwnNoticesResult.NoApiKeySpecifiedError -> {
                // todo: trigger error event
                println("WegliService.OwnNoticesResult.NoApiKeySpecifiedError")
            }
            is WegliService.OwnNoticesResult.Success -> {
                _viewState.update {
                    it.copy(ownNoticesState = ViewState.OwnNoticesState.Data(notices.ownNotices))
                }
            }
        }
    }
}