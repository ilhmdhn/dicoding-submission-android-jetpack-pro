package com.ilhmdhn.movieapps.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowFavoriteViewModelTest {

    private lateinit var viewModel: TvShowFavoriteViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp(){
        viewModel = TvShowFavoriteViewModel(movieRepository)
    }

    @Test
    fun getFavoriteTvShow() {
        val dummyFavoriteTvShow = pagedList
        `when`(dummyFavoriteTvShow.size).thenReturn(5)
        val favoriteTvShow = MutableLiveData<PagedList<TvShowEntity>>()
        favoriteTvShow.value = dummyFavoriteTvShow
        `when`(movieRepository.getTvFavorite()).thenReturn(favoriteTvShow)
        val tvShowEntities = viewModel.getFavoriteTvShow().value
        verify(movieRepository).getTvFavorite()
        assertNotNull(tvShowEntities)
        assertEquals(5, tvShowEntities?.size)
        viewModel.getFavoriteTvShow().observeForever(observer)
        verify(observer).onChanged(dummyFavoriteTvShow)
    }
}