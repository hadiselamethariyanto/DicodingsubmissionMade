package com.bwx.made.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.MoviesUseCase

class InfoViewModel(private val repository: MoviesUseCase) : ViewModel() {

    fun getCastsMovie(movieId: Int) = repository.getCreditsMovie(movieId).asLiveData()
}