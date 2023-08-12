package com.example.youtube.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.base.BaseActivity
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.databinding.ActivityDetailBinding
import com.example.youtube.ui.details.adapter.DetailAdapter
import com.example.youtube.ui.details.viewModel.DetailViewModel
import com.example.youtube.ui.playVideo.PlayVideoActivity
import com.example.youtube.ui.playlists.PlaylistsActivity.Companion.ITEM_LIST
import com.example.youtube.utils.ConnectionLiveData

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    private val modelPlaylist by lazy { intent.extras?.getSerializable(ITEM_LIST) as Playlist.Item }
    private val adapter by lazy { DetailAdapter(this::onClick) }

    override val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    private fun onClick(item: PlaylistItem.Item) {
        val intent = Intent(this, PlayVideoActivity::class.java)
        val bundle= Bundle()
        bundle.putSerializable(PLAYLIST_ITEM_KEY,item)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getPlaylistItem(modelPlaylist.id).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.addList(it.data?.items as MutableList<Playlist.Item>)
                    binding.tvDescription.text = modelPlaylist.snippet.description
                    binding.tvTitle.text = modelPlaylist.snippet.title
                    binding.progressBar.isVisible = false
                    binding.tvVideoCount.text = "${modelPlaylist.contentDetails.itemCount} video"
                }

                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                }

                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    override fun initViews() {
        super.initViews()
        binding.tvBack.setOnClickListener {
            finish()
        }
        binding.recyclerView.adapter = adapter

    }

    override fun inflateViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun isConnection() {
        super.isConnection()
        ConnectionLiveData(application).observe(this) {
            if (it) {
                binding.internetConnection.visibility = View.VISIBLE
                binding.noConnection.visibility = View.GONE
            } else {
                binding.internetConnection.visibility = View.GONE
                binding.noConnection.visibility = View.VISIBLE
                initViewModel()
            }
        }
    }

    companion object{
        const val PLAYLIST_ITEM_KEY = "playlistItem"
    }

}