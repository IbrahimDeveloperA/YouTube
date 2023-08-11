package com.example.youtube.ui.playlists.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.databinding.ItemListsBinding
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.utils.loadImage

class PlaylistAdapter(val onClick: (Playlist.Item) -> Unit, ) :
    Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private var list = mutableListOf<Playlist.Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<Playlist.Item>) {
        this.list = list as ArrayList<Playlist.Item>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            ItemListsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class PlaylistViewHolder(private val binding: ItemListsBinding) :
        ViewHolder(binding.root) {
        fun bind(playlist: Playlist.Item) {
            binding.tvQuantityVideo.text =
                playlist.contentDetails.itemCount.toString() + "video series" ?: "ololo"
            binding.tvNamePlaylist.text = playlist.snippet.title ?: "ololo"
            binding.imgView.loadImage(playlist.snippet.thumbnails.default.url) ?: "ololo"
            itemView.setOnClickListener {
                onClick.invoke(playlist)
            }
        }

    }
}