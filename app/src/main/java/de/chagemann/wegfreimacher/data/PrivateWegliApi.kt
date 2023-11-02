package de.chagemann.wegfreimacher.data

import retrofit2.http.GET
import retrofit2.http.Header

interface PrivateWegliApi {

    @GET("notices")
    suspend fun getNotices(
        @Header("X-API-KEY") apiToken: String
    ): List<NoticeDto>
}