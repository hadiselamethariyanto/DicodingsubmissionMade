package com.bwx.made.ui.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bwx.core.domain.model.Genre
import com.bwx.made.R
import com.bwx.made.databinding.ItemGenreBinding

class GenresAdapter : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    private val list = ArrayList<Genre>()
    private var selectedPosition = -1

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<Genre>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(genre: Genre, position: Int) {
            binding.tvGenreName.text = genre.name

            if (selectedPosition == position) {
                itemView.background =
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.background_genre_selected
                    )
                binding.tvGenreName.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            } else {
                itemView.background =
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.background_genre_not_selected
                    )
                binding.tvGenreName.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.primary
                    )
                )
            }

            itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
                onItemClickCallback.onItemClicked(genre)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItem(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Genre)
    }
}