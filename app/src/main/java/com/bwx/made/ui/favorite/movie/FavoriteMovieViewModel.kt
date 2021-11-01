package com.bwx.made.ui.favorite.movie

import androidx.lifecycle.ViewModel
import com.bwx.made.core.domain.usecase.CinemaUseCase

class FavoriteMovieViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    fun getFavMovies() = cinemaUseCase.getFavoriteMovies()
}