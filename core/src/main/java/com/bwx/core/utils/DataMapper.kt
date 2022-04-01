package com.bwx.core.utils

import com.bwx.core.data.source.local.entity.CastEntity
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.SeasonEntity
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.data.source.remote.response.MoviesItem
import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Season
import com.bwx.core.domain.model.Tv

object DataMapper {

    fun mapMovieResponsesToEntities(input: List<MoviesItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map { response ->
            val tourism = MovieEntity(
                id = response.id,
                title = response.title,
                overview = response.overview,
                poster_path = response.posterPath,
                backdrop_path = response.backdropPath,
                release_date = response.releaseDate,
                runtime = 0,
                vote_average = response.voteAverage,
                isFav = false,
                genres = "",
                created_time = System.currentTimeMillis().toString()
            )
            movieList.add(tourism)
        }
        return movieList
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                release_date = it.release_date,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path,
                vote_average = it.vote_average,
                runtime = it.runtime,
                isFav = it.isFav,
                genres = it.genres
            )
        }

    fun mapCastEntitiesToDomain(input: List<CastEntity>): List<Cast> =
        input.map {
            Cast(
                id = it.id,
                movie_id = it.movie_id,
                character = it.character,
                name = it.name,
                profile_path = it.profile_path
            )
        }

    fun mapTvEntitiesToDomain(input: List<TvEntity>): List<Tv> =
        input.map {
            Tv(
                tv_id = it.tv_id,
                first_air_date = it.first_air_date,
                overview = it.overview,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path,
                vote_average = it.vote_average,
                name = it.name,
                number_of_seasons = it.number_of_seasons,
                isFav = it.isFav,
                genres = it.genres
            )
        }

    fun mapSeasonEntitiesToDomain(input: List<SeasonEntity>): List<Season> = input.map {
        Season(it.id, it.name, it.tv_id, it.poster_path)
    }


    fun mapMovieEntityToDomain(input: MovieEntity) = Movie(
        id = input.id,
        title = input.title,
        overview = input.overview,
        release_date = input.release_date,
        poster_path = input.poster_path,
        backdrop_path = input.backdrop_path,
        vote_average = input.vote_average,
        runtime = input.runtime,
        isFav = input.isFav,
        genres = input.genres
    )

    fun mapTvEntityToDomain(input: TvEntity) = Tv(
        tv_id = input.tv_id,
        first_air_date = input.first_air_date,
        overview = input.overview,
        poster_path = input.poster_path,
        backdrop_path = input.backdrop_path,
        vote_average = input.vote_average,
        name = input.name,
        number_of_seasons = input.number_of_seasons,
        isFav = input.isFav,
        genres = input.genres
    )


    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        release_date = input.release_date,
        poster_path = input.poster_path,
        backdrop_path = input.backdrop_path,
        vote_average = input.vote_average,
        runtime = input.runtime,
        isFav = input.isFav,
        genres = input.genres,
        created_time = System.currentTimeMillis().toString()
    )

    fun mapTvDomainToEntity(input: Tv) = TvEntity(
        tv_id = input.tv_id,
        first_air_date = input.first_air_date,
        overview = input.overview,
        poster_path = input.poster_path,
        backdrop_path = input.backdrop_path,
        vote_average = input.vote_average,
        name = input.name,
        number_of_seasons = input.number_of_seasons,
        isFav = input.isFav,
        genres = input.genres
    )

}