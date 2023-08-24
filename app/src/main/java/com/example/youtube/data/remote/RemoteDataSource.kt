package com.example.youtube.data.remote

import com.example.youtube.BuildConfig
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.base.BaseDataSource
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.data.remote.model.Videos
import org.koin.dsl.module

val remoteDataSource = module {
    factory { RemoteDataSource(get())}
}

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getPlaylist(): Resource<Playlist> {
        return getResult {
            apiService.getPlaylists(
                BuildConfig.API_KEY, "snippet,contentDetails",
                "UCWOA1ZGywLbqmigxE4Qlvuw", 10
            )
        }
    }

    suspend fun getPlaylistItem(playlistItem: String): Resource<Playlist> {
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