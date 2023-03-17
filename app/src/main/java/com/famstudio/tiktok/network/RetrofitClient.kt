package com.famstudio.tiktok.network

import com.famstudio.tiktok.util.BaseUrlProvider.getInterCeptor
import com.famstudio.tiktok.util.BaseUrlProvider.provideBaseURL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroBuilder {

    companion object {
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(provideBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterCeptor())
                .build()
        }
        val api: ApiInterface by lazy {
            retrofit.create(ApiInterface::class.java)
        }

    }


}