package de.chagemann.wegfreimacher

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.chagemann.wegfreimacher.data.ChargeDto
import de.chagemann.wegfreimacher.data.IWegliService
import de.chagemann.wegfreimacher.data.NoticeDto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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
        val ownNotices: ImmutableList<NoticeDto> = persistentListOf(),
    )

    suspend fun loadCharges() {
        val charges = wegliService.getAllCharges() ?: listOf()
        _viewState.update {
            it.copy(availableCharges = charges.toImmutableList())
        }
    }

    suspend fun loadOwnNotices() {
        val notices = wegliService.getOwnNotices() ?: listOf()
        _viewState.update {
            it.copy(ownNotices = notices.toImmutableList())
        }
    }
}