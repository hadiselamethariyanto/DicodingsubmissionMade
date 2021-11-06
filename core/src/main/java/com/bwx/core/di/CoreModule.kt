package com.bwx.core.di

import androidx.room.Room
import com.bwx.core.BuildConfig
import com.bwx.core.data.CinemasRepository
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.room.CinemaDatabase
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.data.source.remote.network.ApiService
import com.bwx.core.domain.repository.ICinemaRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<CinemaDatabase>().cinemaDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            CinemaDatabase::class.java, "Cinema.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<ICinemaRepository> { CinemasRepository(get(), get()) }
}