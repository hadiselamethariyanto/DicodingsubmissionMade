package com.bwx.core.data.source.repository

import androidx.paging.*
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.repository.IMoviesRepository
import com.bwx.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingPopularMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(10),
            remoteMediator = MoviesRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource
            )
        ) {
            localDataSource.getPagingSourceMovies()
        }.flow
    }
}