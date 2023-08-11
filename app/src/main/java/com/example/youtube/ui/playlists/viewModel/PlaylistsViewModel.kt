package com.example.youtube.ui.playlists.viewModel

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.base.BaseViewModel
import com.example.youtube.data.remote.model.Playlist

class PlaylistsViewModel : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<Playlist>> {
        return App.repository.getPlaylists()
    }

}