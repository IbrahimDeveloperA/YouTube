package com.example.youtube.ui.playVideo.viewModel

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.base.BaseViewModel
import com.example.youtube.data.remote.model.Videos

class PlayVideoViewModel:BaseViewModel() {

    fun getVideo(id:String):LiveData<Resource<Videos>>{
        return App.repository.getVideo(id)
    }
}