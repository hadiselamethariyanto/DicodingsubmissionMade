package com.bwx.made.core.data.source.remote

import com.bwx.made.api.ApiConfig
import com.bwx.made.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.Exception

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

    suspend fun getMovies(): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = ApiConfig.getApiService().getPopularMovies(API_KEY, LANGUAGE, 1)
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

    suspend fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = ApiConfig.getApiService().getDetailMovie(movieId, API_KEY, LANGUAGE)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCreditsMovie(movieId: Int): Flow<ApiResponse<List<CastItem>>> {
        return flow {
            try {
                val response = ApiConfig.getApiService().getCreditsMovie(movieId, API_KEY, LANGUAGE)
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
                val response = ApiConfig.getApiService().getPopularTv(API_KEY, LANGUAGE, 1)
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
                val response = ApiConfig.getApiService().getDetailTV(tvId, API_KEY, LANGUAGE)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}