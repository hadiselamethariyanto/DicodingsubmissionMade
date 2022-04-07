package com.bwx.made.ui.detail_tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.domain.usecase.TvUseCase
import kotlinx.coroutines.launch

class DetailTvViewModel(private val tvUseCase: TvUseCase) : ViewModel() {

    private lateinit var detailTv: LiveData<Resource<TvEntity>>

    fun getDetailTV(tvId: Int) {
        detailTv = tvUseCase.getDetailTV(tvId).asLiveData()
    }

    fun setFavorite() {
        val tv = detailTv.value
        if (tv != null) {
            viewModelScope.launch {
//                val newState = !tv.data!!.isFav
//                cinemaUseCase.setFavoriteTv(tv.data!!, newState)
            }
        }
    }

    fun getSeasonTv(tv_id: Int) = tvUseCase.getSeasonTv(tv_id).asLiveData()


    fun getData() = detailTv
}