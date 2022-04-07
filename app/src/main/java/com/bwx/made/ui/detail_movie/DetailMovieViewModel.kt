package com.bwx.made.ui.detail_movie

import androidx.lifecycle.*
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieEntity>>

    fun getDetailMovie(movieId: Int) {
        detailMovie = moviesUseCase.getDetailMovie(movieId).asLiveData()
    }

    fun getMovieVideo(movieId: Int) = moviesUseCase.getMovieVideos(movieId).asLiveData()

    fun setFavorite() {
        val movie = detailMovie.value
        if (movie != null) {
            viewModelScope.launch {
                moviesUseCase.setFavoriteMovie(movie.data!!)
            }
        }
    }

    fun getFavoriteMovie(movieId: Int) =
        moviesUseCase.getFavoriteMovie(movieId).asLiveData()

    fun getData() = detailMovie

}