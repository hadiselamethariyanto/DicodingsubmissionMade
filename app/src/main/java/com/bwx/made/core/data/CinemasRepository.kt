package com.bwx.made.core.data

import com.bwx.made.core.data.source.local.LocalDataSource
import com.bwx.made.core.data.source.local.entity.CastEntity
import com.bwx.made.core.data.source.local.entity.MovieEntity
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.core.data.source.remote.network.ApiResponse
import com.bwx.made.core.data.source.remote.RemoteDataSource
import com.bwx.made.core.data.source.remote.response.*
import com.bwx.made.core.domain.model.Cast
import com.bwx.made.core.domain.model.Movie
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.core.domain.repository.ICinemaRepository
import com.bwx.made.core.utils.DataMapper
import com.bwx.made.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CinemasRepository(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    ICinemaRepository {

    override fun getListMovie(sort: String): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<MoviesItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies(sort).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>(appExecutors) {
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

                val movie = MovieEntity(
                    id = data.id,
                    title = data.title,
                    overview = data.overview,
                    poster_path = data.posterPath,
                    backdrop_path = data.backdropPath,
                    release_date = data.releaseDate,
                    runtime = data.runtime,
                    vote_average = data.voteAverage,
                    isFav = false,
                    genres = genres.toString()
                )

                localDataSource.updateMovie(movie)

            }

        }.asFlow()
    }

    override fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>> {
        return object : NetworkBoundResource<List<Cast>, List<CastItem>>(appExecutors) {
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

    override fun getListTV(sort: String): Flow<Resource<List<Tv>>> {
        return object :
            NetworkBoundResource<List<Tv>, List<TVItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Tv>> {
                return localDataSource.getAllTv(sort).map {
                    DataMapper.mapTvEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Tv>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TVItem>>> =
                remoteDataSource.getListTv()

            override suspend fun saveCallResult(data: List<TVItem>) {
                val tvList = ArrayList<TvEntity>()
                for (response in data) {
                    val tv = TvEntity(
                        tv_id = response.id,
                        first_air_date = response.firstAirDate,
                        overview = response.overview,
                        poster_path = response.posterPath,
                        backdrop_path = response.backdropPath.toString(),
                        vote_average = response.voteAverage,
                        name = response.name,
                        number_of_seasons = response.number_of_seasons,
                        isFav = false,
                        genres = ""
                    )
                    tvList.add(tv)
                }
                localDataSource.insertTV(tvList)
            }
        }.asFlow()

    }

    override fun getDetailTV(tvId: Int): Flow<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, DetailTVResponse>(appExecutors) {

            override fun loadFromDB(): Flow<Tv> =
                localDataSource.getDetailTv(tvId).map {
                    DataMapper.mapTvEntityToDomain(it)
                }

            override fun shouldFetch(data: Tv?): Boolean =
                data != null && data.genres == ""

            override suspend fun createCall(): Flow<ApiResponse<DetailTVResponse>> =
                remoteDataSource.getDetailTv(tvId)

            override suspend fun saveCallResult(data: DetailTVResponse) {
                val genres = StringBuilder().append("")

                for (g in data.genres.indices) {
                    if (g < data.genres.size - 1) {
                        genres.append("${data.genres[g].name}, ")
                    } else {
                        genres.append(data.genres[g].name)
                    }
                }

                val tv = TvEntity(
                    tv_id = data.id,
                    first_air_date = data.firstAirDate,
                    overview = data.overview,
                    poster_path = data.posterPath,
                    backdrop_path = data.backdropPath,
                    vote_average = data.voteAverage,
                    name = data.name,
                    number_of_seasons = data.numberOfSeasons,
                    isFav = false,
                    genres = genres.toString()
                )

                localDataSource.updateTv(tv)

//                val seasonList = ArrayList<SeasonEntity>()
//                for (x in data.seasons!!.indices) {
//                    val season = SeasonEntity(name = data.seasons[x].name)
//                    seasonList.add(season)
//                }
//                localDataSource.insertSeasonTv(seasonList)
            }

        }.asFlow()
    }

    override fun getFavoriteTv(): Flow<List<Tv>> {
        return localDataSource.getFavoriteTv().map {
            DataMapper.mapTvEntitiesToDomain(it)
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteTv(tv: Tv, state: Boolean) {
        val tvEntity = DataMapper.mapTvDomainToEntity(tv)
        localDataSource.setFavoriteTv(tvEntity, state)
    }

    override suspend fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        localDataSource.setFavoriteMovie(movieEntity, state)
    }

}