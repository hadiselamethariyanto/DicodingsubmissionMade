package com.bwx.made.ui.detail_tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.core.domain.usecase.CinemaUseCase
import com.bwx.made.vo.Resource
import kotlinx.coroutines.launch

class DetailTvViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    private lateinit var detailTv: LiveData<Resource<Tv>>

    fun getDetailTV(tvId: Int) {
        detailTv = cinemaUseCase.getDetailTV(tvId).asLiveData()
    }

    fun setFavorite() {
        val tv = detailTv.value
        if (tv != null) {
            viewModelScope.launch {
                val newState = !tv.data!!.isFav
                cinemaUseCase.setFavoriteTv(tv.data, newState)
            }
        }
    }

    fun getData() = detailTv
}