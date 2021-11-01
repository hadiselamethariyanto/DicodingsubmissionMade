package com.bwx.made.ui.detail_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bwx.made.core.domain.model.Movie
import com.bwx.made.core.domain.usecase.CinemaUseCase
import com.bwx.made.vo.Resource

class DetailMovieViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<Movie>>

    fun getDetailMovie(movieId: Int) {
        detailMovie = cinemaUseCase.getDetailMovie(movieId)
    }

    fun getCastMovie(movieId: Int) = cinemaUseCase.getCreditsMovie(movieId)

    fun setFavorite() {
        val movie = detailMovie.value
        if (movie != null) {
            val newState = !movie.data!!.isFav
            cinemaUseCase.setFavoriteMovie(movie.data, newState)
        }
    }

    fun getData() = detailMovie

}