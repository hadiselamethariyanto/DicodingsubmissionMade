package com.bwx.made.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.utils.SortUtils
import com.bwx.core.data.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var repository: com.bwx.core.data.CinemasRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setup() {
        viewModel = MoviesViewModel(repository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(20)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(repository.getListMovie(SortUtils.MOVIE_NEW)).thenReturn(movies)
        val movie = viewModel.getListMovies(SortUtils.MOVIE_NEW).value?.data
        verify(repository).getListMovie(SortUtils.MOVIE_NEW)
        assertNotNull(movie)
        assertEquals(20, movie?.size)

        viewModel.getListMovies(SortUtils.MOVIE_NEW).observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

}