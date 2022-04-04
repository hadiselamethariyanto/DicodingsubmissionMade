package com.bwx.core.domain.usecase

import androidx.paging.PagingData
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Genre
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Video
import com.bwx.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val repository: IMoviesRepository) : MoviesUseCase {
    override fun getPagingPopularMovies(genre: Int): Flow<PagingData<MovieEntity>> =
        repository.getPagingPopularMovies(genre)

    override fun getPagingReviewsMovie(movieId: Int): Flow<PagingData<ReviewEntity>> =
        repository.getPagingReviewsMovie(movieId)

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> =
        repository.getDetailMovie(movieId)

    override fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>> =
        repository.getCreditsMovie(movieId)

    override fun getMovieVideos(movieId: Int): Flow<Resource<List<Video>>> =
        repository.getMovieVideos(movieId)

    override fun getGenreTypes(): Flow<Resource<List<Genre>>> = repository.getGenresMovie()

    override fun getFavoriteMovie(movieId: Int): Flow<Boolean> =
        repository.getFavoriteMovie(movieId)

}