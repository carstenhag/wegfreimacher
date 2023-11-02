package de.chagemann.wegfreimacher.data

import retrofit2.http.GET


interface PublicWegliApi {
    @GET("charges.json")
    suspend fun getCharges(): List<ChargeDto>
}