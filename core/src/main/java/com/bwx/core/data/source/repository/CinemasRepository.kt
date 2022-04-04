package com.bwx.core.data.source.repository

import com.bwx.core.data.NetworkBoundResource
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.CastEntity
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.SeasonEntity
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.data.source.remote.network.ApiResponse
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.data.source.remote.response.*
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Season
import com.bwx.core.domain.model.Tv
import com.bwx.core.domain.repository.ICinemaRepository
import com.bwx.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CinemasRepository(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource
) :
    ICinemaRepository {

    override fun getListMovie(sort: String): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies(sort).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data, 1)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, DetailMovieResponse>() {
            override fun loadFromDB(): Flow<MovieEntity> =
                localDataSource.getDetailMovie(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
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

    override fun getListTV(sort: String): Flow<Resource<List<Tv>>> {
        return object :
            NetworkBoundResource<List<Tv>, List<TVItem>>() {
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
        return object : NetworkBoundResource<Tv, DetailTVResponse>() {

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

                val seasonList = ArrayList<SeasonEntity>()
                for (x in data.seasons) {
                    val season =
                        SeasonEntity(
                            id = x.id,
                            name = x.name,
                            tv_id = tvId,
                            x.poster_path.toString()
                        )
                    seasonList.add(season)
                }
                localDataSource.insertSeasonTv(seasonList)
            }

        }.asFlow()
    }

    override fun getSeasonTv(tv_id: Int): Flow<List<Season>> {
        return localDataSource.getSeasonTv(tv_id).map {
            DataMapper.mapSeasonEntitiesToDomain(it)
        }
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

    override suspend fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        localDataSource.setFavoriteMovie(movie)
    }

}