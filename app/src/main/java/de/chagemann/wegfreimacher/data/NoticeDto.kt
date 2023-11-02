package de.chagemann.wegfreimacher.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

// Notice aka Meldung
@Serializable
data class NoticeDto(
    @SerialName("token") val token: String? = null,
    @SerialName("status") val status: NoticeStatus,
    @SerialName("street") val street: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("zip") val zipCode: String? = null,
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
    @SerialName("registration") val registration: String? = null,
    @SerialName("color") val color: String? = null,
    @SerialName("brand") val brand: String? = null,
    @SerialName("charge") val charge: ChargeDto? = null,
    @SerialName("tbnr") val tbnr: String? = null,
    @SerialName("date") val date: String? = null,
    @SerialName("duration") val duration: Int? = null,
    @SerialName("severity") val severity: Int? = null,
    @SerialName("photos") val photos: List<PhotoDto> = listOf(),

    @SerialName("created_at")
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant? = null,

    @SerialName("updated_at")
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant? = null,

    @SerialName("sent_at")
    @Serializable(with = InstantSerializer::class)
    val sentAt: Instant? = null,

    @SerialName("vehicle_empty") val vehicleEmpty: Boolean? = null,
    @SerialName("hazard_lights") val hazardLights: Boolean? = null,
    @SerialName("expired_tuv") val expiredTuv: Boolean? = null,
    @SerialName("expired_eco") val expiredEco: Boolean? = null,
    @SerialName("over_2_8_tons") val over2Point8Tons: Boolean? = null
) {
    @Serializable
    enum class NoticeStatus {
        @SerialName("open")
        OPEN,

        @SerialName("disabled")
        DISABLED,

        @SerialName("analyzing")
        ANALYZING,

        @SerialName("shared")
        SHARED,
    }
}
