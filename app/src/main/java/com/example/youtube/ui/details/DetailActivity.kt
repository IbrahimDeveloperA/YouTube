package com.example.youtube.ui.details

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
import com.example.youtube.ui.playlists.PlaylistsActivity.Companion.DESCRIPTION
import com.example.youtube.ui.playlists.PlaylistsActivity.Companion.ID
import com.example.youtube.ui.playlists.PlaylistsActivity.Companion.TITLE
import com.example.youtube.utils.ConnectionLiveData

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    private val getIntentId by lazy {   intent.getStringExtra(ID)}
    private val getIntentDesc by lazy { intent.getStringExtra(DESCRIPTION)}
    private val getIntentTitle by lazy { intent.getStringExtra(TITLE)}
    private val adapter by lazy { DetailAdapter(this::onClick) }

    override val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    private fun onClick(item: PlaylistItem.Item) {
        Toast.makeText(this, "OLOLOLO", Toast.LENGTH_SHORT).show()
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getPlaylistItem(getIntentId!!).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.recyclerView.adapter = adapter
                    adapter.addList(it.data?.items as MutableList<Playlist.Item>)
                    binding.tvDescription.text = getIntentDesc
                    binding.tvTitle.text = getIntentTitle
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, "Request is success", Toast.LENGTH_SHORT).show()
                }

                Status.ERROR -> {
                    Toast.makeText(this, "Request is failure", Toast.LENGTH_SHORT).show()
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

}