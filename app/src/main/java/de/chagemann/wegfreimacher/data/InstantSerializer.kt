package de.chagemann.wegfreimacher.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.ZonedDateTime

class InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant {
        val timestamp = decoder.decodeString()
        return try {
            Instant.parse(timestamp)
        } catch (e: Exception) {
            ZonedDateTime.parse(timestamp).toInstant()
        }
    }

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }
}
