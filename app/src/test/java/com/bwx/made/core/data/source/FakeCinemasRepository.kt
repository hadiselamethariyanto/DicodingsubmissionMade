package com.bwx.made.core.data.source

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.bwx.made.core.data.NetworkBoundResource
import com.bwx.made.core.data.source.local.LocalDataSource
import com.bwx.made.core.data.source.local.entity.CastEntity
import com.bwx.made.core.data.source.local.entity.MovieEntity
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.core.data.source.remote.network.ApiResponse
import com.bwx.made.core.data.source.remote.RemoteDataSource
import com.bwx.made.core.data.source.remote.response.*
import com.bwx.made.core.domain.repository.CinemasDataSource
import com.bwx.made.core.utils.AppExecutors
import com.bwx.made.core.data.Resource

class FakeCinemasRepository(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : CinemasDataSource {
    override fun getListMovie(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MoviesItem>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<MoviesItem>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        id = response.id,
                        title = response.title,
                        overview = response.overview,
                        poster_path = response.posterPath,
                        backdrop_path = response.backdropPath,
                        release_date = response.releaseDate,
                        runtime = 0,
                        vote_average = response.voteAverage,
                        isFav = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<MovieEntity> =
                localDataSource.getDetailMovie(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data != null && data.genres == null && data.runtime == 0

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

    override fun getCreditsMovie(movieId: Int): LiveData<Resource<PagedList<CastEntity>>> {
        return object : NetworkBoundResource<PagedList<CastEntity>, List<CastItem>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<CastEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getCastMovie(movieId), config).build()
            }

            override fun shouldFetch(data: PagedList<CastEntity>?): Boolean = true

            override fun createCall(): LiveData<ApiResponse<List<CastItem>>> =
                remoteDataSource.getCreditsMovie(movieId)

            override fun saveCallResult(data: List<CastItem>) {
                val listCast = ArrayList<CastEntity>()
                for (response in data) {
                    val cast = CastEntity(
                        character = response.character,
                        name = response.name,
                        profile_path = response.profilePath,
                        movie_id = movieId
                    )
                    listCast.add(cast)
                }

                localDataSource.insertCast(listCast)

            }

        }.asLiveData()
    }

    override fun getListTV(sort: String): LiveData<Resource<PagedList<TvEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvEntity>, List<TVItem>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getAllTv(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                data == null

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
                        backdrop_path = response.backdropPath,
                        vote_average = response.voteAverage,
                        name = response.name,
                        number_of_seasons = response.number_of_seasons,
                        isFav = false
                    )
                    tvList.add(tv)
                }
                localDataSource.insertTV(tvList)
            }
        }.asLiveData()

    }

    override fun getDetailTV(tvId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, DetailTVResponse>(appExecutors) {

            override fun loadFromDb(): LiveData<TvEntity> = localDataSource.getDetailTv(tvId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data != null && data.number_of_seasons == null && data.genres == null

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

            }

        }.asLiveData()
    }

    fun getFavoriteTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }


    fun setFavoriteTv(tvEntity: TvEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTv(tvEntity, state)
        }
    }

    fun setFavoriteMovie(movieEntity: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }

}