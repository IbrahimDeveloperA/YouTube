package com.example.youtube.ui.playlists

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.view.isVisible
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.base.BaseActivity
import com.example.youtube.data.remote.RemoteDataSource
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.databinding.ActivityPlaylistsBinding
import com.example.youtube.repository.Repository
import com.example.youtube.ui.details.DetailActivity
import com.example.youtube.ui.playlists.adapter.PlaylistAdapter
import com.example.youtube.ui.playlists.viewModel.PlaylistsViewModel
import com.example.youtube.utils.ConnectionLiveData
import org.koin.android.ext.android.inject

class PlaylistsActivity : BaseActivity<ActivityPlaylistsBinding, PlaylistsViewModel>() {

    private val adapter = PlaylistAdapter(this::onClick)
    override val viewModel: PlaylistsViewModel by viewModel()

    //inject() будет получать ссылку из DI
    val repository: Repository by inject()
    private val remoteDataSource: RemoteDataSource by inject()
    private fun onClick(playlist: Playlist.Item) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(ITEM_LIST, playlist)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    override fun initViews() {
        super.initViews()
        binding.recyclerView.adapter = adapter
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getPlaylists().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.addList(it.data?.items as MutableList<Playlist.Item>)
                    binding.progressBar.isVisible = false
                }

                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }

                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    override fun isConnection() {
        super.isConnection()
        ConnectionLiveData(application).observe(this) {
            if (it) {
                binding.btnTryAgain.setOnClickListener {
                    binding.internetConnection.visibility = View.VISIBLE
                    binding.noConnection.visibility = View.GONE
                }
            } else {
                binding.internetConnection.visibility = View.GONE
                binding.noConnection.visibility = View.VISIBLE
                initViewModel()
            }
        }
    }

    override fun inflateViewBinding(): ActivityPlaylistsBinding {
        return ActivityPlaylistsBinding.inflate(layoutInflater)
    }

    companion object {
        const val ITEM_LIST = "LIST"
    }

}