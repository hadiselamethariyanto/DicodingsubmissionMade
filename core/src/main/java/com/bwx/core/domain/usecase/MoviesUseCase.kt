package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Genre
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getPagingPopularMovies(genre: Int): Flow<PagingData<MovieEntity>>

    fun getPagingReviewsMovie(movieId: Int): Flow<PagingData<ReviewEntity>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>

    fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>>

    fun getMovieVideos(movieId: Int): Flow<Resource<List<Video>>>

    fun getGenreTypes(): Flow<Resource<List<Genre>>>

    fun getFavoriteMovie(movieId: Int): Flow<Boolean>
}