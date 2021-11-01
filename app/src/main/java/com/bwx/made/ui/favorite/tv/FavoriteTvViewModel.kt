package com.bwx.made.ui.favorite.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.domain.usecase.CinemaUseCase

class FavoriteTvViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    fun getFavTv() = cinemaUseCase.getFavoriteTv().asLiveData()
}