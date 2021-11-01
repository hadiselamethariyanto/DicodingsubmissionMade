package com.bwx.made.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bwx.made.core.data.source.local.LocalDataSource
import com.bwx.made.core.data.source.local.entity.CastEntity
import com.bwx.made.core.data.source.local.entity.MovieEntity
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.core.data.source.remote.ApiResponse
import com.bwx.made.core.data.source.remote.RemoteDataSource
import com.bwx.made.core.data.source.remote.response.*
import com.bwx.made.core.domain.model.Cast
import com.bwx.made.core.domain.model.Movie
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.core.domain.repository.ICinemaRepository
import com.bwx.made.core.utils.DataMapper
import com.bwx.made.utils.AppExecutors
import com.bwx.made.vo.Resource

class CinemasRepository private constructor(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    ICinemaRepository {

    override fun getListMovie(sort: String): LiveData<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, List<MoviesItem>>(appExecutors) {
            override fun loadFromDb(): LiveData<List<Movie>> {
                return Transformations.map(localDataSource.getAllMovies(sort)) {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<MoviesItem>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: Int): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<Movie> =
                Transformations.map(localDataSource.getDetailMovie(movieId)) {
                    DataMapper.mapMovieEntityToDomain(it)
                }

            override fun shouldFetch(data: Movie?): Boolean =
                data != null && data.runtime == 0

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: DetailMovieResponse) {
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

        }.asLiveData()
    }

    override fun getCreditsMovie(movieId: Int): LiveData<Resource<List<Cast>>> {
        return object : NetworkBoundResource<List<Cast>, List<CastItem>>(appExecutors) {
            override fun loadFromDb(): LiveData<List<Cast>> {
                return Transformations.map(localDataSource.getCastMovie(movieId)) {
                    DataMapper.mapCastEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Cast>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<CastItem>>> =
                remoteDataSource.getCreditsMovie(movieId)

            override fun saveCallResult(data: List<CastItem>) {
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

        }.asLiveData()
    }

    override fun getListTV(sort: String): LiveData<Resource<List<Tv>>> {
        return object :
            NetworkBoundResource<List<Tv>, List<TVItem>>(appExecutors) {
            override fun loadFromDb(): LiveData<List<Tv>> {
                return Transformations.map(localDataSource.getAllTv(sort)) {
                    DataMapper.mapTvEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Tv>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TVItem>>> =
                remoteDataSource.getListTv()

            override fun saveCallResult(data: List<TVItem>) {
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
        }.asLiveData()

    }

    override fun getDetailTV(tvId: Int): LiveData<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, DetailTVResponse>(appExecutors) {

            override fun loadFromDb(): LiveData<Tv> =
                Transformations.map(localDataSource.getDetailTv(tvId)) {
                    DataMapper.mapTvEntityToDomain(it)
                }


            override fun shouldFetch(data: Tv?): Boolean =
                data != null && data.genres == ""

            override fun createCall(): LiveData<ApiResponse<DetailTVResponse>> =
                remoteDataSource.getDetailTv(tvId)

            override fun saveCallResult(data: DetailTVResponse) {
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

        }.asLiveData()
    }

    override fun getFavoriteTv(): LiveData<List<Tv>> {
        return Transformations.map(localDataSource.getFavoriteTv()) {
            DataMapper.mapTvEntitiesToDomain(it)
        }
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
        return Transformations.map(localDataSource.getFavoriteMovies()) {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }


    override fun setFavoriteTv(tv: Tv, state: Boolean) {
        appExecutors.diskIO().execute {
            val tvEntity = DataMapper.mapTvDomainToEntity(tv)
            localDataSource.setFavoriteTv(tvEntity, state)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        appExecutors.diskIO().execute {
            val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }

    companion object {
        @Volatile
        private var instance: CinemasRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): CinemasRepository =
            instance ?: synchronized(this) {
                instance ?: CinemasRepository(
                    remoteData,
                    localDataSource, appExecutors
                )
            }
    }


}