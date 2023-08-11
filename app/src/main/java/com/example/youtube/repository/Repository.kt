package com.example.youtube.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.youtube.BuildConfig
import com.example.youtube.core.network.RetrofitClient
import com.example.youtube.core.network.result.Resource
import com.example.youtube.data.remote.ApiService
import com.example.youtube.data.remote.RemoteDataSource
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val apiService: ApiService by lazy { RetrofitClient.create() }

    private val dataSource:RemoteDataSource by lazy {
        RemoteDataSource()
    }

    fun getPlaylists(): LiveData<Resource<Playlist>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getPlaylist()
            emit(response)
        }
    }


    fun getPlaylistItem(playlistItem: String): LiveData<Resource<PlaylistItem>> {

        val dataItem = MutableLiveData<Resource<PlaylistItem>>()

        dataItem.value = Resource.loading()

        apiService.getPlaylistItems(
            apiKey = BuildConfig.API_KEY,
            part = "snippet,contentDetails",
            playlistId = playlistItem,
            maxResult = 10
        )
            .enqueue(object : Callback<PlaylistItem> {
                override fun onResponse(
                    call: Call<PlaylistItem>,
                    response: Response<PlaylistItem>
                ) {
                    if (response.isSuccessful) {
                        dataItem.value = Resource.success(response.body())
                    }
                }

                override fun onFailure(call: Call<PlaylistItem>, t: Throwable) {
                    dataItem.value = Resource.error(t.message.toString(), null, null)
                }

            })
        return dataItem
    }

}

//  val data = MutableLiveData<Resource<Playlist>>()
//            data.value = Resource.loading()
//
//enqueue(object : Callback<Playlist> {
//                override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
//                    if (response.isSuccessful) {
//
//                        data.value = Resource.success(response.body())
//                    }
//                }
//
//                override fun onFailure(call: Call<Playlist>, t: Throwable) {
//                    data.value = Resource.error(t.message.toString(), null, null)
//                    println(t.stackTrace)
//                }
//            })