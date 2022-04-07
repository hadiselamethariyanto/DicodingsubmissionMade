package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.domain.model.Season
import com.bwx.core.domain.model.Tv
import com.bwx.core.domain.repository.ITvRepository
import kotlinx.coroutines.flow.Flow

class TvInteractor(private val repository: ITvRepository) : TvUseCase {
    override fun getPagingPopularTv(): Flow<PagingData<TvEntity>> = repository.getPagingPopularTv()

    override fun getDetailTV(tvId: Int): Flow<Resource<TvEntity>> = repository.getDetailTV(tvId)

    override fun getFavoriteTv(): Flow<List<Tv>> = repository.getFavoriteTv()

    override fun getSeasonTv(tv_id: Int): Flow<List<Season>> = repository.getSeasonTv(tv_id)

    override suspend fun setFavoriteTv(tv: Tv, state: Boolean) = repository.setFavoriteTv(tv, state)
}