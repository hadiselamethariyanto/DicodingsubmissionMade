package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.domain.repository.ITvRepository
import kotlinx.coroutines.flow.Flow

class TvInteractor(private val repository: ITvRepository) : TvUseCase {
    override fun getPagingPopularTv(): Flow<PagingData<TvEntity>> = repository.getPagingPopularTv()
}