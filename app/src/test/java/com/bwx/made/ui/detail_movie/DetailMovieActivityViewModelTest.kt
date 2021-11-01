package com.bwx.made.ui.detail_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.data.source.local.entity.CastEntity
import com.bwx.made.core.data.source.local.entity.MovieEntity
import com.bwx.made.utils.DataDummy
import com.bwx.made.core.data.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieActivityViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel
    private var dummyMovie = DataDummy.generateDetailMovie()
    private var movieId = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pagedList: PagedList<CastEntity>

    @Mock
    private lateinit var repository: CinemasRepository

    @Mock
    private lateinit var castObserver: Observer<Resource<PagedList<CastEntity>>>

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Before
    fun setupMovie() {
        viewModel = DetailMovieViewModel(repository)
    }

    @Test
    fun getMovie() {
        val dummyMovie = Resource.success(DataDummy.generateDetailMovie())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovie

        `when`(repository.getDetailMovie(movieId!!)).thenReturn(movie)

        viewModel.getDetailMovie(movieId!!)
        viewModel.getData().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }


    @Test
    fun getCast() {
        val dummyCast = Resource.success(pagedList)
        `when`(dummyCast.data?.size).thenReturn(5)
        val cast = MutableLiveData<Resource<PagedList<CastEntity>>>()
        cast.value = dummyCast

        `when`(repository.getCreditsMovie(movieId!!)).thenReturn(cast)
        val listCast = viewModel.getCastMovie(movieId!!).value?.data
        verify(repository).getCreditsMovie(movieId!!)

        assertNotNull(listCast)
        assertEquals(5, listCast?.size)

        viewModel.getCastMovie(movieId!!).observeForever(castObserver)
        verify(castObserver).onChanged(dummyCast)
    }

    @Test
    fun setFavorite() {
        val dummyDetailMovie = Resource.success(DataDummy.generateDetailMovie())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(movieId?.let { repository.getDetailMovie(it) }).thenReturn(movie)

        movieId?.let { viewModel.getDetailMovie(it) }
        viewModel.setFavorite()
        verify(repository).setFavoriteMovie(movie.value!!.data as MovieEntity, true)
        verifyNoMoreInteractions(observer)
    }
}