package com.bwx.made.ui.detail_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bwx.made.core.domain.model.Movie
import com.bwx.made.core.domain.usecase.CinemaUseCase
import com.bwx.made.core.data.Resource
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<Movie>>

    fun getDetailMovie(movieId: Int) {
        detailMovie = cinemaUseCase.getDetailMovie(movieId).asLiveData()
    }

    fun getCastMovie(movieId: Int) = cinemaUseCase.getCreditsMovie(movieId).asLiveData()

    fun setFavorite() {
        val movie = detailMovie.value
        if (movie != null) {
            viewModelScope.launch {
                val newState = !movie.data!!.isFav
                cinemaUseCase.setFavoriteMovie(movie.data, newState)
            }
        }
    }

    fun getData() = detailMovie

}