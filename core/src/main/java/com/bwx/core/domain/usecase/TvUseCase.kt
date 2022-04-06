package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.source.local.entity.TvEntity
import kotlinx.coroutines.flow.Flow

interface TvUseCase {
    fun getPagingPopularTv(): Flow<PagingData<TvEntity>>
}