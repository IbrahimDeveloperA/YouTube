package com.example.youtube.ui.playVideo

import android.view.View
import androidx.core.view.isVisible
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.base.BaseActivity
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.databinding.ActivityPlayVideoBinding
import com.example.youtube.ui.details.DetailActivity.Companion.PLAYLIST_ITEM_KEY
import com.example.youtube.ui.playVideo.viewModel.PlayVideoViewModel
import com.example.youtube.utils.ConnectionLiveData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayVideoActivity : BaseActivity<ActivityPlayVideoBinding, PlayVideoViewModel>() {

    private val modelVideo by lazy {
        intent.extras?.getSerializable(PLAYLIST_ITEM_KEY) as PlaylistItem.Item
    }
  //  private val adapter by lazy { PlayVideoAdapter() }

    override fun initViewModel() {
        super.initViewModel()
        lifecycle.addObserver(binding.youtubePlayerView)
//        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                intent.getStringExtra("id1")?.let { youTubePlayer.loadVideo(it, 0f) }
//            }
//        })
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
//                val videoId = "S0Q4gqBUs7c"
                youTubePlayer.loadVideo(modelVideo.contentDetails.videoId, 0f)
            }
        })

        binding.tvTitle.text = modelVideo.snippet.title
        binding.tvDesc.text = modelVideo.snippet.description
        viewModel.getVideo(modelVideo.contentDetails.videoId).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {

                //    adapter.addList(it.data?.items as MutableList<Videos.Item>)
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
    //    binding.recyclerView.adapter = adapter
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

    override fun inflateViewBinding(): ActivityPlayVideoBinding {
        return ActivityPlayVideoBinding.inflate(layoutInflater)
    }

    override val viewModel: PlayVideoViewModel by viewModel()

}