package de.chagemann.wegfreimacher.data

import de.chagemann.wegfreimacher.de.chagemann.wegfreimacher.data.ImageUploadRequestResponseDto
import de.chagemann.wegfreimacher.selectimages.ImageUploadContainerDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PrivateWegliApi {

    @GET("notices")
    suspend fun getNotices(
        @Header("X-API-KEY") apiToken: String
    ): List<NoticeDto>

    @POST("uploads")
    suspend fun requestImageUploadUrl(
        @Header("X-API-KEY") apiToken: String,
        @Body imageUploadContainerDto: ImageUploadContainerDto
    ): ImageUploadRequestResponseDto

    @PUT("{uploadUrl}")
    suspend fun uploadImage(
        @Path("uploadUrl") url: String,
        @HeaderMap headers: Map<String, String>,
        @Body body: RequestBody,
    ): Response<String>
}
