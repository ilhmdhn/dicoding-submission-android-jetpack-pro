package com.ilhmdhn.movieapps.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ilhmdhn.movieapps.utils.DataDummy
import com.ilhmdhn.movieapps.utils.DataDummy.getTvShowDetail
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.ui.detail.tvshow.DetailTvShowViewModel
import com.ilhmdhn.movieapps.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {

    private lateinit var tvShowViewModel: DetailTvShowViewModel
    private val dummyTvShow = DataDummy.getTvShowData()[0]
    private val tvShowId = dummyTvShow.id
    private val dummyDetailTvShow = getTvShowDetail(tvShowId)[0]

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var detailTvShowObserver: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        tvShowViewModel = DetailTvShowViewModel(movieRepository)
        tvShowViewModel.setSelected(tvShowId)
    }

    @Test
    fun getShow() {
        val dummyDetailTvShow = Resource.success(dummyDetailTvShow)
        val detailTvShow = MutableLiveData<Resource<TvShowEntity>>()
        detailTvShow.value = dummyDetailTvShow
        tvShowViewModel.detailTvShow = detailTvShow
        dummyDetailTvShow.data?.let { doNothing(). `when`(movieRepository).setFavoriteTvShow(it, true) }
        tvShowViewModel.setFavorite()
        verify(movieRepository).setFavoriteTvShow(detailTvShow.value?.data as TvShowEntity, true)
    }

    @Test
    fun setFavoriteTvShow(){
        val dummyDetaiTvShow = Resource.success(dummyDetailTvShow)
        val detailTvShow = MutableLiveData<Resource<TvShowEntity>>()
        detailTvShow.value = dummyDetaiTvShow
        tvShowViewModel.detailTvShow = detailTvShow
        dummyDetaiTvShow.data?.let { doNothing(). `when`(movieRepository).setFavoriteTvShow(it, true) }
        tvShowViewModel.setFavorite()
        verify(movieRepository)
            .setFavoriteTvShow(detailTvShow.value?.data as TvShowEntity, true)
    }
}