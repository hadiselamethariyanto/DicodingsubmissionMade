package com.bwx.core.domain.repository

import androidx.paging.PagingData
import com.bwx.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getPagingPopularMovies(): Flow<PagingData<Movie>>
}