package com.ilhmdhn.movieapps.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ilhmdhn.movieapps.utils.DataDummy
import com.ilhmdhn.movieapps.utils.DataDummy.getMovieDetail
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.ui.detail.movie.DetailMovieViewModel
import com.ilhmdhn.movieapps.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var movieViewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.getMovieData()[0]
    private val movieId = dummyMovie.id
    private val dummyDetailMovie = getMovieDetail(movieId)[0]

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var detailMovieObserver: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        movieViewModel = DetailMovieViewModel(movieRepository)
        movieViewModel.setSelected(movieId)
    }

    @Test
    fun getShow() {
        val dummyDetailMovie = Resource.success(dummyDetailMovie)
        val detailMovie = MutableLiveData<Resource<MovieEntity>>()
        detailMovie.value = dummyDetailMovie

        `when`(movieRepository.getMovieDetail(movieId)).thenReturn(detailMovie)
        movieViewModel.detailMovie.observeForever(detailMovieObserver)
        assertNotNull(dummyDetailMovie.data)
        verify(detailMovieObserver).onChanged(dummyDetailMovie)
    }

    @Test
    fun setFavoriteMovie(){
        val dummyDetailMovie = Resource.success(dummyDetailMovie)
        val detailMovie = MutableLiveData<Resource<MovieEntity>>()
        detailMovie.value = dummyDetailMovie
        movieViewModel.detailMovie = detailMovie
        dummyDetailMovie.data?.let { doNothing(). `when`(movieRepository).setFavoriteMovie(it, true) }
        movieViewModel.setFavorite()
        verify(movieRepository)
            .setFavoriteMovie(detailMovie.value?.data as MovieEntity, true)
    }
}