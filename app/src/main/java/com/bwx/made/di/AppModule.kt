package com.bwx.made.di

import com.bwx.core.domain.usecase.CinemaInteractor
import com.bwx.core.domain.usecase.CinemaUseCase
import com.bwx.made.ui.detail_movie.DetailMovieViewModel
import com.bwx.made.ui.detail_tv.DetailTvViewModel
import com.bwx.made.ui.favorite.movie.FavoriteMovieViewModel
import com.bwx.made.ui.favorite.tv.FavoriteTvViewModel
import com.bwx.made.ui.movies.MoviesViewModel
import com.bwx.made.ui.tv.TvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<CinemaUseCase> { CinemaInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { DetailTvViewModel(get()) }
}