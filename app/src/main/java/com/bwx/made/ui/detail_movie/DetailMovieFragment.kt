package com.bwx.made.ui.detail_movie

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bwx.core.data.Resource
import com.bwx.core.domain.model.Movie
import com.bwx.made.R
import com.bwx.made.databinding.ContentDetailMovieBinding
import com.bwx.made.databinding.FragmentDetailMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailBinding: ContentDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
        detailBinding = binding.detailContent
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(MOVIE_KEY) ?: 0
        getDetailMovie(id)
    }

    private fun getDetailMovie(id: Int) {
        viewModel.getDetailMovie(id)
        viewModel.getData().observe(viewLifecycleOwner, movieObserver)
    }

    private val movieObserver = Observer<Resource<Movie>> { movie ->
        when (movie) {
            is Resource.Loading -> setLoading(true)
            is Resource.Success -> {
                if (movie.data != null) {
                    setLoading(false)
                    populateMovie(movie.data!!)
                }
            }
            is Resource.Error -> {
                setLoading(false)
            }
        }
    }

    private fun populateMovie(movie: Movie) {
        with(detailBinding) {
            tvTitleMovie.text = movie.title
            tvCategoryMovie.text = movie.genres
            tvReleasedateMovie.text = movie.release_date
            tvDurationMovie.text = movie.runtime.toString()
            tvOverview.text = movie.overview
        }

        if (movie.isFav) {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.red))
        } else {
            binding.fab.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.white))
        }

        Glide.with(this)
            .load(resources.getString(R.string.image_path, movie.backdrop_path))
            .transform(RoundedCorners(20))
            .centerCrop()
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(detailBinding.imgPoster)
    }

    private fun setLoading(boolean: Boolean) {
        if (boolean) {
            binding.content.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.content.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val MOVIE_KEY = "movie_key"
    }
}