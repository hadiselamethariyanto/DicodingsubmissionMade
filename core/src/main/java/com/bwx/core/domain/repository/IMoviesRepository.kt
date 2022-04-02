package com.bwx.core.domain.repository

import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getPagingPopularMovies(): Flow<PagingData<MovieEntity>>

    fun getPagingReviewsMovie(movieId: Int): Flow<PagingData<ReviewEntity>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>

    fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>>
}