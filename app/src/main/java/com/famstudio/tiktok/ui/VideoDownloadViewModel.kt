package com.famstudio.tiktok.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.famstudio.tiktok.interactor.VideoGetUseCase
import com.famstudio.tiktok.model.TikTokDataModel
import com.famstudio.tiktok.model.request.GetVideoRequestModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoDownloadViewModel : ViewModel() {
    var videoDownloadUseCase: VideoGetUseCase = VideoGetUseCase()
    var myResponse: MutableLiveData<TikTokDataModel> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val error : MutableLiveData<Boolean> = MutableLiveData()
    fun getWeather(requestModel: GetVideoRequestModel, success:(TikTokDataModel)->Unit, failed:()->Unit) {
        isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            videoDownloadUseCase.invoke(requestModel, {
                myResponse.postValue(it)
                isLoading.postValue(false)
                error.postValue(false)
                success.invoke(it)
            }, {
                error.postValue(true)
                isLoading.postValue(false)
                failed.invoke()
            })
        }

    }


}