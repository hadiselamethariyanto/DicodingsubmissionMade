package com.bwx.core.data.source.remote

import com.bwx.core.BuildConfig
import com.bwx.core.data.source.remote.network.ApiResponse
import com.bwx.core.data.source.remote.network.ApiService
import com.bwx.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.Exception

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getMovies(): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getPopularMovies(API_KEY, LANGUAGE, 1)
                val dataArray = response.results
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(dataArray))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPagingMovies(page: Int?): MovieResponse = apiService.getPopularMovies(
        API_KEY,
        LANGUAGE, page
    )

    suspend fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(movieId, API_KEY, LANGUAGE)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCreditsMovie(movieId: Int): Flow<ApiResponse<List<CastItem>>> {
        return flow {
            try {
                val response = apiService.getCreditsMovie(movieId, API_KEY, LANGUAGE)
                val dataArray = response.cast
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListTv(): Flow<ApiResponse<List<TVItem>>> {
        return flow {
            try {
                val response = apiService.getPopularTv(API_KEY, LANGUAGE, 1)
                val dataArray = response.results
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(dataArray))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailTv(tvId: Int): Flow<ApiResponse<DetailTVResponse>> {
        return flow {
            try {
                val response = apiService.getDetailTV(tvId, API_KEY, LANGUAGE)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
        private const val LANGUAGE = BuildConfig.LANGUAGE
    }


}