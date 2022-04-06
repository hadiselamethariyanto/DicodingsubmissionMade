package com.bwx.core.data.source.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.domain.model.Season
import com.bwx.core.domain.model.Tv
import com.bwx.core.domain.repository.ITvRepository
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class TvRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ITvRepository {
    override fun getPagingPopularTv(): Flow<PagingData<TvEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = TvRemoteMediator(localDataSource, remoteDataSource)
        ) {
            localDataSource.getPagingSourceTv()
        }.flow
    }

    override fun getDetailTV(tvId: Int): Flow<Resource<Tv>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTv(): Flow<List<Tv>> {
        TODO("Not yet implemented")
    }

    override fun getSeasonTv(tv_id: Int): Flow<List<Season>> {
        TODO("Not yet implemented")
    }

    override suspend fun setFavoriteTv(tv: Tv, state: Boolean) {
        TODO("Not yet implemented")
    }

}