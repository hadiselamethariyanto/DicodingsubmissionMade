package com.bwx.core.domain.repository

import androidx.paging.PagingData
import com.bwx.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getPagingPopularMovies(): Flow<PagingData<MovieEntity>>
}