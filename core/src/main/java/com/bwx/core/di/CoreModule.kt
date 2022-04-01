package com.bwx.core.di

import androidx.room.Room
import com.bwx.core.BuildConfig
import com.bwx.core.data.source.repository.CinemasRepository
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.room.CinemaDatabase
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.data.source.remote.network.ApiService
import com.bwx.core.data.source.repository.MoviesRemoteMediator
import com.bwx.core.data.source.repository.MoviesRepository
import com.bwx.core.domain.repository.ICinemaRepository
import com.bwx.core.domain.repository.IMoviesRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
    val factor = SupportFactory(passphrase)

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
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
            .build()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .addInterceptor(loggingInterceptor)
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
    single { MoviesRemoteMediator(get(), get()) }
    single<ICinemaRepository> { CinemasRepository(get(), get()) }
    single<IMoviesRepository> { MoviesRepository(get(), get()) }
}