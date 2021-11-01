package com.bwx.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailTVResponse(

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("seasons")
    val seasons: List<SeasonsItem>,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("name")
    val name: String

)

data class SeasonsItem(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("id")
    val id: Int
)
