package com.famstudio.tiktok.network

import com.famstudio.tiktok.model.TikTokDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {
    @GET("/")
    fun getVideo(
        @Header("x-rapidapi-host") apiHost: String,
        @Header("x-rapidapi-key") apiKey: String,
        @Query("url") url:String
    ): Call<TikTokDataModel>
}