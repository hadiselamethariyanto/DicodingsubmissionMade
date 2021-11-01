package com.bwx.made.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bwx.made.api.ApiConfig
import com.bwx.made.core.data.source.remote.response.*
import com.bwx.made.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        private const val API_KEY = "e3b408a268a2828c1a10803a4f55415c"
        private const val LANGUAGE = "en-US"

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getMovies(): LiveData<ApiResponse<List<MoviesItem>>> {
        EspressoIdlingResource.increment()
        val movieResult = MutableLiveData<ApiResponse<List<MoviesItem>>>()
        val client = ApiConfig.getApiService().getPopularMovies(API_KEY, LANGUAGE, 1)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                movieResult.value =
                    ApiResponse.success(response.body()?.results as List<MoviesItem>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return movieResult
    }

    fun getDetailMovie(movieId: Int): LiveData<ApiResponse<DetailMovieResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<DetailMovieResponse>>()

        val client = ApiConfig.getApiService().getDetailMovie(movieId, API_KEY, LANGUAGE)
        client.enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                EspressoIdlingResource.decrement()
                resultDetailMovie.value =
                    ApiResponse.success(response.body() as DetailMovieResponse)
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getDetailMovie onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultDetailMovie
    }

    fun getCreditsMovie(movieId: Int): LiveData<ApiResponse<List<CastItem>>> {
        EspressoIdlingResource.increment()
        val castResult = MutableLiveData<ApiResponse<List<CastItem>>>()
        val client = ApiConfig.getApiService().getCreditsMovie(movieId, API_KEY, LANGUAGE)
        client.enqueue(object : Callback<CreditsResponse> {
            override fun onResponse(
                call: Call<CreditsResponse>,
                response: Response<CreditsResponse>
            ) {
                castResult.value = ApiResponse.success(response.body()?.cast as List<CastItem>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getCreditsMovie onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return castResult
    }

    fun getListTv(): LiveData<ApiResponse<List<TVItem>>> {
        EspressoIdlingResource.increment()
        val tvResult = MutableLiveData<ApiResponse<List<TVItem>>>()
        val client = ApiConfig.getApiService().getPopularTv(API_KEY, LANGUAGE, 1)
        client.enqueue(object : Callback<TvResponse> {
            override fun onResponse(
                call: Call<TvResponse>,
                response: Response<TvResponse>
            ) {
                tvResult.value =
                    ApiResponse.success(response.body()?.results as List<TVItem>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getListTv onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return tvResult
    }

    fun getDetailTv(tvId: Int): LiveData<ApiResponse<DetailTVResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTv = MutableLiveData<ApiResponse<DetailTVResponse>>()

        val client = ApiConfig.getApiService().getDetailTV(tvId, API_KEY, LANGUAGE)
        client.enqueue(object : Callback<DetailTVResponse> {
            override fun onResponse(
                call: Call<DetailTVResponse>,
                response: Response<DetailTVResponse>
            ) {
                EspressoIdlingResource.decrement()
                resultDetailTv.value = ApiResponse.success(response.body() as DetailTVResponse)
            }

            override fun onFailure(call: Call<DetailTVResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getListTv onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultDetailTv
    }

}