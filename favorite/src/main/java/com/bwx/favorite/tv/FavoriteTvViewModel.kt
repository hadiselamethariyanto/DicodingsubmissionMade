package com.bwx.favorite.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.TvUseCase

class FavoriteTvViewModel(private val tvUseCase: TvUseCase) : ViewModel() {

    fun getFavTv() = tvUseCase.getFavoriteTv().asLiveData()
}