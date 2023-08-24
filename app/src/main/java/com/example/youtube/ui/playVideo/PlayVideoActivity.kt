package com.example.youtube.ui.playVideo

import android.view.View
import androidx.core.view.isVisible
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.base.BaseActivity
import com.example.youtube.data.remote.model.Playlist
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
        intent.extras?.getSerializable(PLAYLIST_ITEM_KEY) as Playlist.Item
    }
    //  private val adapter by lazy { PlayVideoAdapter() }

    override fun initViewModel() {
        super.initViewModel()
        lifecycle.addObserver(binding.videoView);

        binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
              //  youTubePlayer.loadVideo(modelVideo.contentDetails.videoId, 0f)
                youTubePlayer.cueVideo(modelVideo.contentDetails.videoId, 0f)
               // youTubePlayer.loadVideo(modelVideo.contentDetails.videoId, 0f)
            }
        })
        binding.tvTitle.text = modelVideo.snippet.title
        binding.tvDesc.text = modelVideo.snippet.description
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