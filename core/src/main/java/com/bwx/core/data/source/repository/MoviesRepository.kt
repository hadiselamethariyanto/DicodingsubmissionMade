package com.bwx.core.data.source.repository

import androidx.paging.*
import com.bwx.core.data.NetworkBoundResource
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.CastEntity
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.ReviewEntity
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.data.source.remote.network.ApiResponse
import com.bwx.core.data.source.remote.response.CastItem
import com.bwx.core.data.source.remote.response.DetailMovieResponse
import com.bwx.core.data.source.remote.response.GenresResponse
import com.bwx.core.data.source.remote.response.VideoItem
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Genre
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Video
import com.bwx.core.domain.repository.IMoviesRepository
import com.bwx.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalPagingApi::class)
class MoviesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMoviesRepository {

    override fun getPagingPopularMovies(genre: Int): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(10),
            remoteMediator = MoviesRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource
            )
        ) {
            localDataSource.getPagingSourceMovies(genre)
        }.flow
    }

    override fun getPagingReviewsMovie(movieId: Int): Flow<PagingData<ReviewEntity>> {
        return Pager(
            config = PagingConfig(10),
            remoteMediator = ReviewsRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
                movieId = movieId
            )
        ) {
            localDataSource.getPagingSourceReviewsMovie(movieId)
        }.flow
    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>() {
            override fun loadFromDB(): Flow<Movie> =
                localDataSource.getDetailMovie(movieId).map {
                    DataMapper.mapMovieEntityToDomain(it)
                }

            override fun shouldFetch(data: Movie?): Boolean =
                data != null && data.runtime == 0

            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(movieId)

            override suspend fun saveCallResult(data: DetailMovieResponse) {
                val genres = StringBuilder().append("")

                for (g in data.genres.indices) {
                    if (g < data.genres.size - 1) {
                        genres.append("${data.genres[g].name}, ")
                    } else {
                        genres.append(data.genres[g].name)
                    }
                }

                localDataSource.updateMovie(data.id, data.runtime, genres.toString())

            }

        }.asFlow()
    }

    override fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>> {
        return object : NetworkBoundResource<List<Cast>, List<CastItem>>() {
            override fun loadFromDB(): Flow<List<Cast>> {
                return localDataSource.getCastMovie(movieId).map {
                    DataMapper.mapCastEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Cast>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CastItem>>> =
                remoteDataSource.getCreditsMovie(movieId)

            override suspend fun saveCallResult(data: List<CastItem>) {
                val listCast = ArrayList<CastEntity>()
                for (response in data) {
                    val cast = CastEntity(
                        id = response.id,
                        character = response.character,
                        name = response.name,
                        profile_path = response.profilePath.toString(),
                        movie_id = movieId
                    )
                    listCast.add(cast)
                }

                localDataSource.insertCast(listCast)

            }

        }.asFlow()
    }

    override fun getMovieVideos(movieId: Int): Flow<Resource<List<Video>>> {
        return object : NetworkBoundResource<List<Video>, List<VideoItem>>() {
            override fun loadFromDB(): Flow<List<Video>> {
                return localDataSource.getMovieVideos(movieId).map {
                    DataMapper.mapVideoEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Video>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<VideoItem>>> =
                remoteDataSource.getMovieVideos(movieId)

            override suspend fun saveCallResult(data: List<VideoItem>) {
                if (data.isNotEmpty()) {
                    localDataSource.insertMovieVideos(
                        DataMapper.mapVideoResponseToEntities(
                            data,
                            movieId
                        )
                    )
                }
            }

        }.asFlow()
    }

    override fun getGenresMovie(): Flow<Resource<List<Genre>>> {
        return object : NetworkBoundResource<List<Genre>, GenresResponse>() {
            override fun loadFromDB(): Flow<List<Genre>> {
                return localDataSource.getGenreTypes().map {
                    DataMapper.mapGenreTypeEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Genre>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<GenresResponse>> =
                remoteDataSource.getGenreTypes()

            override suspend fun saveCallResult(data: GenresResponse) {
                if (data.genres.isNotEmpty()) {
                    localDataSource.insertGenreTypes(DataMapper.mapGenreTypesResponseToEntities(data.genres))
                }
            }

        }.asFlow()
    }

    override fun getFavoriteMovie(movieId: Int): Flow<Boolean> {
        return localDataSource.getFavoriteMovie(movieId)
    }
}