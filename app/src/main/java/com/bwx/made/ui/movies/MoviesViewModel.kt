package com.bwx.made.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.domain.usecase.CinemaUseCase

class MoviesViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    fun getListMovies(sort: String) = cinemaUseCase.getListMovie(sort).asLiveData()

}