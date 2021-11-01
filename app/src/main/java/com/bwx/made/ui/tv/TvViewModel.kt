package com.bwx.made.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.CinemaUseCase

class TvViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    fun getListTv(sort: String) = cinemaUseCase.getListTV(sort).asLiveData()

}