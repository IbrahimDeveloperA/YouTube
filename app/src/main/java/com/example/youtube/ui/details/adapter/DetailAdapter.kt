package com.example.youtube.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.databinding.ItemDetailBinding
import com.example.youtube.utils.loadImage

class DetailAdapter(private val onClick: (Playlist.Item) -> Unit) :
    Adapter<DetailAdapter.DetailViewHolder>() {

    private var list = mutableListOf<Playlist.Item>()

    fun addList(list: List<Playlist.Item>) {
        this.list = list as MutableList<Playlist.Item>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class DetailViewHolder(private val binding: ItemDetailBinding) :
        ViewHolder(binding.root) {
        fun onBind(item: Playlist.Item) {
            binding.tvTime.text = item.kind ?: "Пусто"
            binding.tvVideoName.text = item.snippet.title ?: "Пусто"
            binding.ivVideo.loadImage(item.snippet.thumbnails.standard.url!!) ?: "Пусто"
            itemView.setOnClickListener {
                onClick(item)
            }
        }
    }
}
