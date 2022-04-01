package com.example.imageloaderdemo.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {
    private const val TIMEOUT = 30L

    private const val BASE_URL = "https://tw-mobile-xian.github.io/"

    private val mClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        }.build()
    }

    private val mRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createApiService(clazz: Class<T>): T {
        return mRetrofit.create(clazz)
    }
}