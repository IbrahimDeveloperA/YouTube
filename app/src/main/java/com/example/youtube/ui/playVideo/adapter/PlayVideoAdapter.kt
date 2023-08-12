package com.example.youtube.ui.playVideo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.data.remote.model.Videos
import com.example.youtube.databinding.ItemVideoBinding
import com.example.youtube.utils.loadImage

class PlayVideoAdapter: Adapter<PlayVideoAdapter.PlayVideoViewHolder>() {

    private var list = mutableListOf<Videos.Item>()

    fun addList(list: List<Videos.Item>) {
        this.list = list as MutableList<Videos.Item>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayVideoViewHolder {
        return PlayVideoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PlayVideoViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    class PlayVideoViewHolder(private val binding:ItemVideoBinding):ViewHolder(binding.root) {
        fun onBind(item: Videos.Item) {
            binding.imgView.loadImage(item.snippet.thumbnails.standard.url)
            binding.tvTitle.text = item.snippet.title
            binding.tvDesc.text = item.snippet.description
        }

    }
}