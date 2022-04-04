package com.bwx.made.ui.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bwx.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MoviesViewModel(
    private val useCase: MoviesUseCase
) : ViewModel() {
    private val genre = MutableLiveData<Int>()

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movies = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        genre.asFlow().flatMapLatest { useCase.getPagingPopularMovies(it) }.cachedIn(viewModelScope)
    ).flattenMerge(2)


    fun setGenre(genre: Int) {
        clearListCh.trySend(Unit).isSuccess
        this.genre.value = genre
    }

    fun getGenreTypes() = useCase.getGenreTypes().asLiveData()

}