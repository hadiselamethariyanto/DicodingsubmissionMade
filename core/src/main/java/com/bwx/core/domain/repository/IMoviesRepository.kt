package com.bwx.core.domain.repository

import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getPagingPopularMovies(genre: Int): Flow<PagingData<MovieEntity>>

    fun getPagingReviewsMovie(movieId: Int): Flow<PagingData<ReviewEntity>>

    fun getDetailMovie(movieId: Int): Flow<Resource<MovieEntity>>

    fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>>

    fun getMovieVideos(movieId: Int): Flow<Resource<List<Video>>>

    fun getGenresMovie(): Flow<Resource<List<Genre>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getFavoriteMovie(movieId: Int): Flow<Boolean>

    suspend fun setFavoriteMovie(movie: MovieEntity)
}