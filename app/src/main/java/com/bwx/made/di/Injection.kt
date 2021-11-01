package com.bwx.made.di

import android.content.Context
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.data.source.local.LocalDataSource
import com.bwx.made.core.data.source.local.room.CinemaDatabase
import com.bwx.made.core.data.source.remote.RemoteDataSource
import com.bwx.made.core.domain.usecase.CinemaInteractor
import com.bwx.made.core.domain.usecase.CinemaUseCase
import com.bwx.made.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): CinemasRepository {
        val database = CinemaDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.cinemaDao())
        val appExecutors = AppExecutors()
        val remoteDataSource = RemoteDataSource.getInstance()
        return CinemasRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideCinemaUseCase(context: Context): CinemaUseCase {
        val repository = provideRepository(context)
        return CinemaInteractor(repository)
    }
}