package com.example.youtube.ui.playVideo

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.base.BaseActivity
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.data.remote.model.Videos
import com.example.youtube.databinding.ActivityPlayVideoBinding
import com.example.youtube.ui.details.DetailActivity.Companion.PLAYLIST_ITEM_KEY
import com.example.youtube.ui.playVideo.adapter.PlayVideoAdapter
import com.example.youtube.ui.playVideo.viewModel.PlayVideoViewModel
import com.example.youtube.utils.ConnectionLiveData

class PlayVideoActivity : BaseActivity<ActivityPlayVideoBinding, PlayVideoViewModel>() {

    private val modelVideo by lazy {
        intent.extras?.getSerializable(PLAYLIST_ITEM_KEY) as PlaylistItem.Item
    }
    private val adapter by lazy { PlayVideoAdapter() }


    override fun initViewModel() {
        super.initViewModel()
        viewModel.getVideo(modelVideo.contentDetails.videoId).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter.addList(it.data?.items as MutableList<Videos.Item>)
                    binding.progressBar.isVisible = false
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
        binding.recyclerView.adapter = adapter
    }

    override fun initListener() {
        super.initListener()
        binding.tvBack.setOnClickListener {
            finish()
        }
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

    override fun inflateViewBinding(): ActivityPlayVideoBinding {
        return ActivityPlayVideoBinding.inflate(layoutInflater)
    }

    override val viewModel: PlayVideoViewModel by lazy {
        ViewModelProvider(this)[PlayVideoViewModel::class.java]
    }

}