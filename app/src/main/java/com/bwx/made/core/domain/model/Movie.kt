package com.bwx.made.core.domain.model

data class Movie(
    var id: Int,
    var title: String,
    var overview: String,
    var release_date: String,
    var poster_path: String,
    var backdrop_path: String,
    var vote_average: Double,
    var runtime: Int,
    var isFav: Boolean,
    var genres: String
)