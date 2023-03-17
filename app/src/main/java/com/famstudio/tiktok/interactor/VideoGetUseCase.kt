package com.famstudio.tiktok.interactor

import com.famstudio.tiktok.model.TikTokDataModel
import com.famstudio.tiktok.model.request.GetVideoRequestModel
import com.famstudio.tiktok.repository.GetVideoRepository
import com.famstudio.tiktok.util.BaseUrlProvider.getAPIHost
import com.famstudio.tiktok.util.BaseUrlProvider.getAuthorizationKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class VideoGetUseCase {
    var getVideoRepository: GetVideoRepository = GetVideoRepository()
    private val job = CoroutineScope(Dispatchers.IO)

    fun invoke(getVideoRequestModel: GetVideoRequestModel, isSuccess:(TikTokDataModel)->Unit, isFailed:(String)->Unit) {
        val weather = getVideoRepository.getVideo(
            getAPIHost(),
            getAuthorizationKey(),
           getVideoRequestModel.url
        )
        job.launch {
            weather.execute().apply {
                when (this.isSuccessful){
                    true->isSuccess.invoke(this.body()!!)
                    false->isFailed.invoke(this.message())
                }
            }
        }
    }
    fun cancel(onCancelled:()->Unit){
        job.cancel("Job Cancelled")
        onCancelled.invoke()
    }
}