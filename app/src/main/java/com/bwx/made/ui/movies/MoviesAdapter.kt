package com.bwx.made.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.made.R
import com.bwx.core.domain.model.Movie
import com.bwx.made.databinding.ItemsMovieBinding
import com.bwx.made.ui.detail_movie.DetailMovieActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private var list = ArrayList<Movie>()
    fun updateData(new: List<Movie>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = list[position]
        holder.bind(movie)
    }

    inner class MoviesViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                var format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                var newDate: Date? = null
                try {
                    newDate = format.parse(movie.release_date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                format = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
                val date: String = format.format(newDate)

                tvItemTitle.text = movie.title
                tvItemDate.text = date
                tvItemVoteAverage.text = movie.vote_average.toString()

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie.id)
                    itemView.context.startActivity(intent)
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

    override fun getItemCount(): Int {
        return list.size
    }

}