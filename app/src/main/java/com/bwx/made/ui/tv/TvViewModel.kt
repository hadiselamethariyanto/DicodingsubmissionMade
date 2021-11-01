package com.bwx.made.ui.tv

import androidx.lifecycle.ViewModel
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.domain.usecase.CinemaUseCase

class TvViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    fun getListTv(sort: String) = cinemaUseCase.getListTV(sort)

}