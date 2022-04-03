package com.bwx.made.ui.movie_videos

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bwx.core.domain.model.Video
import com.bwx.made.databinding.ItemVideoBinding

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    private var list = ArrayList<Video>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateDate(new: List<Video>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(video: Video, onItemClickCallback: OnItemClickCallback) {
            binding.text.text = video.name

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(video)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], onItemClickCallback)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Video)
    }
}