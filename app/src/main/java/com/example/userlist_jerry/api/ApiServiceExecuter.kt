package com.example.userlist_jerry.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceExecuter {
    companion object {
        const val BASE_URL = "https://api.github.com"

        const val CONNECT_TIMEOUT_SECOND = 15L
        const val WRITE_TIMEOUT_SECOND = 15L
        const val READ_TIMEOUT_SECOND = 15L

        private val httpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECOND * 1000.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECOND * 1000.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT_SECOND * 1000.toLong(), TimeUnit.MILLISECONDS)
            .build()

        private var builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)

        private fun createRetrofitBuilder(enableFieldNull: Boolean): Retrofit.Builder =
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(createGsonConverterFactory(enableFieldNull))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)

        private fun createGsonConverterFactory(enableFieldNull: Boolean): GsonConverterFactory {
            if (enableFieldNull) {
                return GsonConverterFactory.create(GsonBuilder().serializeNulls().create())
            }
            return GsonConverterFactory.create()
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return createRetrofitBuilder(true).build().create(serviceClass)
        }







    }
}