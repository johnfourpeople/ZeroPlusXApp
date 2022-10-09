package com.zeroplusx.mobile.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zeroplusx.mobile.data.network.ArticlesApi
import com.zeroplusx.mobile.data.network.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthenticator(): Authenticator {
        return TokenAuthenticator
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authenticator: Authenticator): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(authenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json(builderAction = { ignoreUnknownKeys = true })
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): ArticlesApi {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(checkNotNull("application/json".toMediaType())))
            .build()
            .create(ArticlesApi::class.java)
    }
}
