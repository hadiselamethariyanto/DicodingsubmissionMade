package com.bwx.made.api

import com.bwx.made.core.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("3/movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<DetailMovieResponse>

    @GET("3/movie/{movie_id}/credits")
    fun getCreditsMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<CreditsResponse>

    @GET("3/tv/popular")
    fun getPopularTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<TvResponse>


    @GET("3/tv/{tv_id}")
    fun getDetailTV(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<DetailTVResponse>

}