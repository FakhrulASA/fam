package com.famstudio.tiktok.repository

import com.famstudio.tiktok.model.TikTokDataModel
import com.famstudio.tiktok.network.ApiInterface
import com.famstudio.tiktok.network.RetroBuilder
import retrofit2.Call

class GetVideoRepository : ApiInterface {
    override fun getVideo(
        apiHost: String,
        apiKey: String,
        url: String
    ): Call<TikTokDataModel> = RetroBuilder.api.getVideo(apiHost,apiKey,url)
}