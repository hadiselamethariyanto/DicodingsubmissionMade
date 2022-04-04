package com.bwx.made.ui.detail_movie

import androidx.lifecycle.*
import com.bwx.core.domain.usecase.CinemaUseCase
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val cinemaUseCase: CinemaUseCase,
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieEntity>>
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun getDetailMovie(movieId: Int) {
        detailMovie = cinemaUseCase.getDetailMovie(movieId).asLiveData()
    }

    fun getMovieVideo(movieId: Int) = moviesUseCase.getMovieVideos(movieId).asLiveData()

    fun setFavorite() {
        val movie = detailMovie.value
        if (movie != null) {
            viewModelScope.launch {
                val newState = !movie.data!!.isFav
                cinemaUseCase.setFavoriteMovie(movie.data!!, newState)
            }
        }
    }

    fun getFavoriteMovie(movieId: Int) =
        moviesUseCase.getFavoriteMovie(movieId).asLiveData()

    fun getData() = detailMovie

}