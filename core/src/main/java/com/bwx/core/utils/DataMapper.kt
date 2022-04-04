package com.bwx.core.utils

import com.bwx.core.data.source.local.entity.*
import com.bwx.core.data.source.remote.response.*
import com.bwx.core.domain.model.*

object DataMapper {

    fun mapVideoResponseToEntities(input: List<VideoItem>, movieId: Int): List<VideoEntity> =
        input.map {
            VideoEntity(
                id = it.id,
                name = it.name.toString(),
                official = it.official.toString(),
                type = it.type.toString(),
                site = it.site.toString(),
                size = it.size ?: 0,
                publishedAt = it.publishedAt.toString(),
                iso6391 = it.iso6391.toString(),
                iso31661 = it.iso31661.toString(),
                key = it.key.toString(),
                movieId = movieId
            )
        }

    fun mapGenreTypesResponseToEntities(input: List<GenresItem>): List<GenreTypeEntity> {
        val list = ArrayList<GenreTypeEntity>()
        input.map {
            list.add(
                GenreTypeEntity(
                    id = it.id ?: 0,
                    name = it.name.toString()
                )
            )
        }
        list.add(GenreTypeEntity(0, "All"))
        return list
    }


    fun mapGenreTypeEntitiesToDomain(input: List<GenreTypeEntity>): List<Genre> = input.map {
        Genre(
            id = it.id,
            name = it.name
        )
    }

    fun mapVideoEntitiesToDomain(input: List<VideoEntity>): List<Video> = input.map {
        Video(
            id = it.id,
            name = it.name,
            official = it.official,
            type = it.type,
            site = it.site,
            size = it.size,
            publishedAt = it.publishedAt,
            iso6391 = it.iso6391,
            iso31661 = it.iso31661,
            key = it.key
        )
    }

    fun mapMovieGenresResponseToEntities(input: List<MoviesItem>): List<GenreMovieEntity> {
        val genres = ArrayList<GenreMovieEntity>()
        input.map { moviesItem ->
            moviesItem.genre_ids.map {
                val genre = GenreMovieEntity(
                    id = "${moviesItem.id}_${it}",
                    movie_id = moviesItem.id ?: 0,
                    genre_id = it
                )
                genres.add(genre)
            }
        }
        return genres
    }

    fun mapMovieResponsesToEntities(input: List<MoviesItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map { response ->
            val tourism = MovieEntity(
                id = response.id ?: 0,
                title = response.title.toString(),
                overview = response.overview.toString(),
                poster_path = response.posterPath.toString(),
                backdrop_path = response.backdropPath.toString(),
                release_date = response.releaseDate.toString(),
                runtime = 0,
                vote_average = response.voteAverage ?: 0.0,
                isFav = false,
                genres = ""
            )
            movieList.add(tourism)
        }
        return movieList
    }

    fun mapReviewsResponseToEntity(response: ReviewsResponse, movieId: Int): List<ReviewEntity> {
        val list = ArrayList<ReviewEntity>()
        response.results.map {
            val review = ReviewEntity(
                id = it?.id.toString(),
                username = it?.authorDetails?.username.toString(),
                avatar_url = it?.authorDetails?.avatarPath.toString(),
                rating = it?.authorDetails?.rating ?: 0.0,
                content = it?.content.toString(),
                movieId = movieId
            )
            list.add(review)
        }
        return list
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
        genres = input.genres
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