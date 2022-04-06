package com.bwx.core.data.source.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.RemoteKeyEntity
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TvRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, TvEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = localDataSource.getRemoteKey("popular_tv")
                    if (remoteKey.nextPageKey == remoteKey.total_pages) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPageKey.plus(1)
                }
            }

            val tv = remoteDataSource.getPagingTv(loadKey)

            val remoteKeyEntity = RemoteKeyEntity(
                category = "popular_tv",
                tv.page,
                total_pages = tv.totalPages
            )

            Log.d("UHT", tv.results.size.toString())

            if (loadType == LoadType.REFRESH) {
                localDataSource.deleteTv()
                localDataSource.deleteRemoteKey("popular_tv")
            }

            localDataSource.insertRemoteKey(remoteKeyEntity)

            if (tv.results.isNotEmpty()) {
                localDataSource.insertTvs(
                    DataMapper.mapTvResponsesToEntities(
                        tv.results,
                        tv.page
                    )
                )
//                localDataSource.insertGenresMovie(DataMapper.mapMovieGenresResponseToEntities(tv.results))
            }

            return MediatorResult.Success(endOfPaginationReached = tv.results.isEmpty())

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}