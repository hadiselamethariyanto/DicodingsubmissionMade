package com.bwx.made.ui.detail_tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bwx.made.core.data.CinemasRepository
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.utils.DataDummy
import com.bwx.made.core.data.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {

    private lateinit var viewModel: DetailTvViewModel
    private val dummyTv = DataDummy.generateDetailTVDummy()
    private val tvId = dummyTv.tv_id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: CinemasRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvEntity>>

    @Before
    fun setup() {
        viewModel = DetailTvViewModel(repository)
    }

    @Test
    fun getTv() {
        val dummyTv = Resource.success(DataDummy.generateDetailTVDummy())
        val tv = MutableLiveData<Resource<TvEntity>>()
        tv.value = dummyTv

        `when`(repository.getDetailTV(tvId!!)).thenReturn(tv)

        viewModel.getDetailTV(tvId)
        viewModel.getData().observeForever(observer)

        verify(observer).onChanged(dummyTv)
    }

    @Test
    fun setFavorite() {
        val dummyDetailMovie = Resource.success(DataDummy.generateDetailTVDummy())
        val tv = MutableLiveData<Resource<TvEntity>>()
        tv.value = dummyDetailMovie

        `when`(tvId?.let { repository.getDetailTV(it) }).thenReturn(tv)

        tvId?.let { viewModel.getDetailTV(it) }
        viewModel.setFavorite()
        verify(repository).setFavoriteTv(tv.value!!.data as TvEntity, true)
        Mockito.verifyNoMoreInteractions(observer)
    }


}