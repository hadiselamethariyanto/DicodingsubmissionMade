package com.bwx.favorite.di

import com.bwx.favorite.movie.FavoriteMovieViewModel
import com.bwx.favorite.tv.FavoriteTvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvViewModel(get()) }
}