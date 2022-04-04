package com.bwx.made.ui.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.usecase.CinemaUseCase
import com.bwx.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val useCase: MoviesUseCase
) : ViewModel() {
    private var _pagingMovie = MutableLiveData<PagingData<MovieEntity>>()
    val pagingMovie get() = _pagingMovie
    private val genre = MutableLiveData<Int>()

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty<MovieEntity>() },
        genre.asFlow().flatMapLatest { useCase.getPagingPopularMovies(it) }.cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun getListMovies(genre: Int) {
        viewModelScope.launch {
            useCase.getPagingPopularMovies(genre).collectLatest {
                _pagingMovie.value = it
            }
        }
    }

    fun setGenre(genre: Int) {
        clearListCh.offer(Unit)
        this.genre.value = genre
    }

    fun getPagingMovies(genre: Int) = useCase.getPagingPopularMovies(genre).asLiveData()

    fun getGenreTypes() = useCase.getGenreTypes().asLiveData()

}