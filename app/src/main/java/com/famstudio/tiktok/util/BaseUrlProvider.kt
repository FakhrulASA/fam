package com.famstudio.tiktok.util

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object BaseUrlProvider {
    fun provideBaseURL():String{
        return "https://tiktok-video-no-watermark2.p.rapidapi.com";
    }
    fun getAuthorizationKey():String{
        return "ebcf947ed9mshbcf63a69a577529p10403cjsn73e27f724e87"
    }
    fun getAPIHost():String{
        return "tiktok-video-no-watermark2.p.rapidapi.com"
    }
    fun getInterCeptor():OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return client;
    }
}