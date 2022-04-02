package com.bwx.made.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.made.R
import com.bwx.made.databinding.ItemsMovieBinding
import com.bwx.made.utils.Utils

class MoviesAdapter :
    PagingDataAdapter<MovieEntity, MoviesAdapter.MoviesViewHolder>(DataDifferntiator) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MoviesViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {


                tvItemTitle.text = movie.title
                tvItemVoteAverage.text = movie.vote_average.toString()
                if (movie.release_date.isNotEmpty() || movie.release_date == "null") {
                    tvItemDate.text = Utils.formatStringDate(movie.release_date)
                    tvItemDate.visibility = View.VISIBLE
                } else {
                    tvItemDate.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(movie)
                }

                Glide.with(itemView.context)
                    .load(itemView.resources.getString(R.string.image_path, movie.poster_path))
                    .transform(CenterCrop(), RoundedCorners(36))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }

    object DataDifferntiator : DiffUtil.ItemCallback<MovieEntity>() {

        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieEntity)
    }


}