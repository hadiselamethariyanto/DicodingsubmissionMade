package com.bwx.made.core.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bwx.made.core.data.source.local.LocalDataSource
import com.bwx.made.core.data.source.local.entity.CastEntity
import com.bwx.made.core.data.source.local.entity.MovieEntity
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.core.data.source.remote.RemoteDataSource
import com.bwx.made.utils.*
import com.bwx.made.core.data.Resource
import com.bwx.made.core.utils.AppExecutors
import com.bwx.made.core.utils.SortUtils
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CinemasRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val cinemasRepository = FakeCinemasRepository(remote, local, appExecutors)

    private val movieList = DataDummy.generateDummyMovies()
    private val movieId = movieList[0].id
    private val movieDetail = DataDummy.generateDetailMovie()

    private val tvList = DataDummy.generateDummyTv()
    private val tvId = tvList[0].tv_id
    private val tvDetail = DataDummy.generateDetailTVDummy()

    private val castList = DataDummy.generateCastMovie()

    @Test
    fun getListMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies(SortUtils.MOVIE_NEW)).thenReturn(dataSourceFactory)
        cinemasRepository.getListMovie(SortUtils.MOVIE_NEW)

        val movieEntities =
            Resource.success(PagedListUtils.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies(SortUtils.MOVIE_NEW)
        assertNotNull(movieEntities)
        assertEquals(movieList.size, movieEntities.data?.size)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetail = MutableLiveData<MovieEntity>()
        dummyDetail.value = DataDummy.generateDetailMovie()
        `when`(movieId?.let { local.getDetailMovie(it) }).thenReturn(dummyDetail)

        val movie = LiveDataTestUtil.getValue(cinemasRepository.getDetailMovie(movieId!!))
        verify(remote).getDetailMovie(movieId)
        assertNotNull(movie)
        assertEquals(movieDetail, movie)
    }

    @Test
    fun getCreditsMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, CastEntity>
        `when`(movieId?.let { local.getCastMovie(it) }).thenReturn(dataSourceFactory)
        movieId?.let { cinemasRepository.getCreditsMovie(it) }

        val castEntity =
            Resource.success(PagedListUtils.mockPagedList(DataDummy.generateCastMovie()))
        movieId?.let { verify(local).getCastMovie(it) }
        assertNotNull(castEntity)
        assertEquals(castList.size, castEntity.data?.size)
    }

    @Test
    fun getListTV() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getAllTv(SortUtils.TV_NEW)).thenReturn(dataSourceFactory)
        cinemasRepository.getListTV(SortUtils.TV_NEW)

        val tvEntities =
            Resource.success(PagedListUtils.mockPagedList(DataDummy.generateDummyTv()))
        verify(local).getAllTv(SortUtils.TV_NEW)
        assertNotNull(tvEntities)
        assertEquals(tvList.size, tvEntities.data?.size)
    }

    @Test
    fun getDetailTV() {
        val dummyDetail = MutableLiveData<TvEntity>()
        dummyDetail.value = DataDummy.generateDetailTVDummy()
        `when`(tvId?.let { local.getDetailTv(it) }).thenReturn(dummyDetail)

        val tv = LiveDataTestUtil.getValue(cinemasRepository.getDetailTV(tvId!!))
        verify(remote).getDetailTv(tvId)
        assertNotNull(tv)
        assertEquals(tvDetail, tv)
    }
}