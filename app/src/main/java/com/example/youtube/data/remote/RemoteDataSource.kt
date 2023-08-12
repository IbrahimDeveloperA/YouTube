package com.example.youtube.data.remote

import com.example.youtube.BuildConfig
import com.example.youtube.core.network.RetrofitClient
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.base.BaseDataSource
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.data.remote.model.Videos

class RemoteDataSource() : BaseDataSource() {

    private val apiService: ApiService by lazy { RetrofitClient.create() }

    suspend fun getPlaylist(): Resource<Playlist> {
        return getResult {
            apiService.getPlaylists(
                BuildConfig.API_KEY, "snippet,contentDetails",
                "UCWOA1ZGywLbqmigxE4Qlvuw", 10
            )
        }
    }

    suspend fun getPlaylistItem(playlistItem: String): Resource<PlaylistItem> {
        return getResult {
            apiService.getPlaylistItems(BuildConfig.API_KEY, part = "snippet,contentDetails",
                playlistId = playlistItem, maxResult = 10
            )
        }
    }

    suspend fun getVideo(id: String): Resource<Videos> {
        return getResult {
            apiService.getVideo(apiKey = BuildConfig.API_KEY, part = "snippet,contentDetails", id)
        }
    }
}