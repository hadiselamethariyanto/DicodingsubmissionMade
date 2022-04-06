package com.bwx.made.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.CinemaUseCase
import com.bwx.core.domain.usecase.TvUseCase

class TvViewModel(private val tvUseCase: TvUseCase) : ViewModel() {

    fun getPagingPopularTv() = tvUseCase.getPagingPopularTv().asLiveData()

}