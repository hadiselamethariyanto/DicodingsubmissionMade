package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getPagingPopularMovies(): Flow<PagingData<MovieEntity>>
}