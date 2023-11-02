package de.chagemann.wegfreimacher.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChargeDto(
    @SerialName("tbnr") val tbnr: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("fine") val fine: String? = null,
    @SerialName("bkat") val lawReference: String? = null,
    @SerialName("penalty") val penalty: String? = null,
    @SerialName("points") val points: Int? = null,
)