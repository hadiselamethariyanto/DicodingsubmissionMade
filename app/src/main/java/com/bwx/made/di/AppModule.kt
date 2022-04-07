package com.bwx.made.di

import com.bwx.core.domain.usecase.*
import com.bwx.made.ui.detail_movie.DetailMovieViewModel
import com.bwx.made.ui.detail_tv.DetailTvViewModel
import com.bwx.made.ui.info.InfoViewModel
import com.bwx.made.ui.movie_reviews.MovieReviewsViewModel
import com.bwx.made.ui.movie_videos.MovieVideosViewModel
import com.bwx.made.ui.movies.MoviesViewModel
import com.bwx.made.ui.tv.TvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MoviesUseCase> { MoviesInteractor(get()) }
    factory<TvUseCase> { TvInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { DetailTvViewModel(get()) }
    viewModel { MovieReviewsViewModel(get()) }
    viewModel { InfoViewModel(get()) }
    viewModel { MovieVideosViewModel(get()) }
}