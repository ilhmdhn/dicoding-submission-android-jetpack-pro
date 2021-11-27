package com.ilhmdhn.movieapps.ui.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.remote.response.ResultItems
import com.ilhmdhn.movieapps.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<ListTvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<ListTvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieRepository)
    }

    @Test
    fun getTvShow() {
        val dummyTvShow = Resource.success(pagedList)
        `when`(dummyTvShow.data?.size).thenReturn(3)
        val tvShow = MutableLiveData<Resource<PagedList<ListTvShowEntity>>>()
        tvShow.value = dummyTvShow
        `when`(movieRepository.getAllTv()).thenReturn(tvShow)
        val tvShowEntities = viewModel.getTvShow().value?.data
        verify(movieRepository).getAllTv()
        assertNotNull(tvShowEntities)
        assertEquals(3, tvShowEntities?.size)
        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}