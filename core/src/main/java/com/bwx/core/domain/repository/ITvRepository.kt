package com.bwx.core.domain.repository

import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.domain.model.Season
import com.bwx.core.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface ITvRepository {
    fun getPagingPopularTv(): Flow<PagingData<TvEntity>>
    fun getDetailTV(tvId: Int): Flow<Resource<TvEntity>>
    fun getFavoriteTv(): Flow<List<Tv>>
    fun getSeasonTv(tv_id: Int): Flow<List<Season>>
    suspend fun setFavoriteTv(tv: Tv, state: Boolean)
}