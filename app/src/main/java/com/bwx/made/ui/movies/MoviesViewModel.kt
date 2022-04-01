package com.bwx.made.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.CinemaUseCase
import com.bwx.core.domain.usecase.MoviesUseCase

class MoviesViewModel(private val useCase: MoviesUseCase) : ViewModel() {

    fun getListMovies() = useCase.getPagingPopularMovies().asLiveData()

}