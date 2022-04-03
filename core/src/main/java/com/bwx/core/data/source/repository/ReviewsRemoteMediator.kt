package com.bwx.core.data.source.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.RemoteKeyEntity
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.data.source.remote.response.ReviewsResponse
import com.bwx.core.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ReviewsRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val movieId: Int
) : RemoteMediator<Int, ReviewEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = localDataSource.getRemoteKey(movieId.toString())
                    if (remoteKey.nextPageKey == remoteKey.total_pages) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPageKey.plus(1)
                }
            }

            val reviews = remoteDataSource.getReviewsMovie(movieId, loadKey)
            val remoteKeyEntity = RemoteKeyEntity(
                category = movieId.toString(),
                reviews.page,
                total_pages = reviews.totalPages
            )
            localDataSource.insertRemoteKey(remoteKeyEntity)

            if (reviews.results.isNotEmpty()) {
                localDataSource.insertReviews(
                    DataMapper.mapReviewsResponseToEntity(reviews, movieId)
                )
            }

            return MediatorResult.Success(endOfPaginationReached = reviews.results.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}