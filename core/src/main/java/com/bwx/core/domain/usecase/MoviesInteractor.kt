package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val repository: IMoviesRepository) : MoviesUseCase {
    override fun getPagingPopularMovies(): Flow<PagingData<MovieEntity>> =
        repository.getPagingPopularMovies()

    override fun getPagingReviewsMovie(movieId: Int): Flow<PagingData<ReviewEntity>> =
        repository.getPagingReviewsMovie(movieId)

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> =
        repository.getDetailMovie(movieId)

    override fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>> =
        repository.getCreditsMovie(movieId)
}