package de.chagemann.wegfreimacher

import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.chagemann.wegfreimacher.data.IWegliService
import de.chagemann.wegfreimacher.data.PrivateWegliApi
import de.chagemann.wegfreimacher.data.PublicWegliApi
import de.chagemann.wegfreimacher.data.WegliService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.ConnectionSpec
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (Constants.isDebug) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            encodeDefaults = true // for requests we want default values to be included
            isLenient = true // quoted booleans and unquoted strings are allowed
            coerceInputValues = true // coerce unknown enum values to default
            explicitNulls = false
        }
    }

    @Provides
    fun providePublicApi(
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): PublicWegliApi {
        val baseUrl = "https://weg.li/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(PublicWegliApi::class.java)
    }

    @Provides
    fun providePrivateApi(
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): PrivateWegliApi {
        val baseUrl = "https://weg.li/api/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(PrivateWegliApi::class.java)
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        val sharedPreferencesName = "wegfreimacher"
        return appContext.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsBindsModule {
    @Binds
    abstract fun provideWegliService(wegliService: WegliService): IWegliService
}
