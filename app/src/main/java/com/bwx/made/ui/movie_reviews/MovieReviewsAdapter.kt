package com.bwx.made.ui.movie_reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.made.R
import com.bwx.made.databinding.ItemReviewBinding

class MovieReviewsAdapter :
    PagingDataAdapter<ReviewEntity, MovieReviewsAdapter.ViewHolder>(DataDifferntiator) {
    class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(review: ReviewEntity) {
            with(binding) {
                tvUsername.text = review.username
                tvContent.text = review.content

                Glide.with(itemView.context)
                    .load(itemView.resources.getString(R.string.image_path, review.avatar_url))
                    .placeholder(R.drawable.ic_loading).error(R.drawable.ic_error).circleCrop()
                    .into(imgUser)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindItem(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<ReviewEntity>() {

        override fun areItemsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
            return oldItem == newItem
        }
    }

}