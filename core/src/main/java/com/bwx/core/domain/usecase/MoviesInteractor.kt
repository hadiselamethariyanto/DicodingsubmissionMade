package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val repository: IMoviesRepository) : MoviesUseCase {
    override fun getPagingPopularMovies(): Flow<PagingData<MovieEntity>> =
        repository.getPagingPopularMovies()
}