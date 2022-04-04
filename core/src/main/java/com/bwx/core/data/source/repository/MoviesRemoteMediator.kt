package com.bwx.core.data.source.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.RemoteKeyEntity
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = localDataSource.getRemoteKey("popular_movies")

                    if (remoteKey.nextPageKey == remoteKey.total_pages) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextPageKey.plus(1)
                }

            }

            val movies = remoteDataSource.getPagingMovies(loadKey)

            val remoteKeyEntity = RemoteKeyEntity(
                category = "popular_movies",
                movies.page,
                total_pages = movies.totalPages
            )

            if (loadType == LoadType.REFRESH) {
                localDataSource.deleteMovie()
                localDataSource.deleteRemoteKey()
            }

            localDataSource.insertRemoteKey(remoteKeyEntity)

            if (movies.results.isNotEmpty()) {
                localDataSource.insertMovies(
                    DataMapper.mapMovieResponsesToEntities(
                        movies.results,
                        movies.page
                    )
                )
                localDataSource.insertGenresMovie(DataMapper.mapMovieGenresResponseToEntities(movies.results))
            }

            return MediatorResult.Success(endOfPaginationReached = movies.results.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}