package com.bwx.core.domain.model

data class Tv(
    var tv_id: Int,
    var first_air_date: String,
    var overview: String,
    var poster_path: String,
    var backdrop_path: String,
    var vote_average: Double,
    var name: String,
    var number_of_seasons: Int,
    var isFav: Boolean,
    var genres: String
)