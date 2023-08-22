package com.example.youtube.ui.playVideo.viewModel

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.base.BaseViewModel
import com.example.youtube.data.remote.model.Videos
import com.example.youtube.repository.Repository

class PlayVideoViewModel (private val repository: Repository): BaseViewModel() {

    fun getVideo(id: String): LiveData<Resource<Videos>> {
        return repository.getVideo(id)
    }
}