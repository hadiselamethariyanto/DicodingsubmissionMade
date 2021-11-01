package com.bwx.made.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.core.utils.SortUtils
import com.bwx.made.core.data.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: CinemasRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>


    @Before
    fun setup() {
        viewModel = TvViewModel(repository)
    }

    @Test
    fun getTvData() {
        val dummyTV = Resource.success(pagedList)
        `when`(dummyTV.data?.size).thenReturn(20)
        val tv = MutableLiveData<Resource<PagedList<TvEntity>>>()
        tv.value = dummyTV

        `when`(repository.getListTV(SortUtils.TV_NEW)).thenReturn(tv)
        val movie = viewModel.getListTv(SortUtils.TV_NEW).value?.data
        Mockito.verify(repository).getListTV(SortUtils.TV_NEW)
        assertNotNull(movie)
        assertEquals(20, movie?.size)

        viewModel.getListTv(SortUtils.TV_NEW).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTV)
    }
}