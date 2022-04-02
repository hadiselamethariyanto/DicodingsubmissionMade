package com.bwx.made.ui.movie_reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.MoviesUseCase

class MovieReviewsViewModel(private val repository: MoviesUseCase) : ViewModel() {

    fun getMovieReviews(movieId: Int) = repository.getPagingReviewsMovie(movieId).asLiveData()
}