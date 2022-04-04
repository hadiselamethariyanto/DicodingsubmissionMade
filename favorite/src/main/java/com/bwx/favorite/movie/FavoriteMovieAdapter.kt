package com.bwx.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.core.domain.model.Movie
import com.bwx.made.R
import com.bwx.made.databinding.ItemsMovieBinding

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {
    private val list = ArrayList<Movie>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<Movie>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(movie: Movie, onItemClickCallback: OnItemClickCallback) {
            with(binding) {


                tvItemTitle.text = movie.title
                tvItemVoteAverage.text = movie.vote_average.toString()
                tvItemDate.text = movie.release_date

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], onItemClickCallback)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }

}