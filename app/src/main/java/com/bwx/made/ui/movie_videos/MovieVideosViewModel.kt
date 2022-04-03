package com.bwx.made.ui.movie_videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.MoviesUseCase

class MovieVideosViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    fun getMovieVideos(movieId: Int) = moviesUseCase.getMovieVideos(movieId).asLiveData()
}