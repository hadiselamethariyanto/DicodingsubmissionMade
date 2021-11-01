package com.bwx.made.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.domain.usecase.CinemaUseCase
import com.bwx.made.di.Injection
import com.bwx.made.ui.detail_movie.DetailMovieViewModel
import com.bwx.made.ui.detail_tv.DetailTvViewModel
import com.bwx.made.ui.favorite.movie.FavoriteMovieViewModel
import com.bwx.made.ui.favorite.tv.FavoriteTvViewModel
import com.bwx.made.ui.movies.MoviesViewModel
import com.bwx.made.ui.tv.TvViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val cinemaUseCase: CinemaUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideCinemaUseCase(context)).apply { instance = this }
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(cinemaUseCase) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(cinemaUseCase) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(cinemaUseCase) as T
            }
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> {
                DetailTvViewModel(cinemaUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteTvViewModel::class.java) -> {
                FavoriteTvViewModel(cinemaUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(cinemaUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}