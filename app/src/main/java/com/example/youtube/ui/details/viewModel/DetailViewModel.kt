package com.example.youtube.ui.details.viewModel

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.base.BaseViewModel
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.repository.Repository

class DetailViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlaylistItem(playlistId: String): LiveData<Resource<PlaylistItem>> {
        return repository.getPlaylistItem(playlistId)
    }

}