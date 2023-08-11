package com.example.youtube.data.remote

import com.example.youtube.BuildConfig
import com.example.youtube.core.network.RetrofitClient
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.base.BaseDataSource
import com.example.youtube.data.remote.model.Playlist

class RemoteDataSource : BaseDataSource() {

    private val apiService: ApiService by lazy { RetrofitClient.create() }

    suspend fun getPlaylist(): Resource<Playlist> {
        return getResult {
            apiService.getPlaylists(
                BuildConfig.API_KEY, "snippet,contentDetails",
                "UCWOA1ZGywLbqmigxE4Qlvuw", 10
            )
        }
    }
}