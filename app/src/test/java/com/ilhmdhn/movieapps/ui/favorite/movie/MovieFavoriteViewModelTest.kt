package com.ilhmdhn.movieapps.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
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
class MovieFavoriteViewModelTest {

    private lateinit var viewModel: MovieFavoriteViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp(){
        viewModel = MovieFavoriteViewModel(movieRepository)
    }
    @Test
    fun getFavoriteMovie() {
        val dummyFavoriteMovie = pagedList
        `when`(dummyFavoriteMovie.size).thenReturn(5)
        val favoriteMovie = MutableLiveData<PagedList<MovieEntity>>()
        favoriteMovie.value= dummyFavoriteMovie
        `when`(movieRepository.getFavoriteMovie()).thenReturn(favoriteMovie)
        val movieEntities = viewModel.getFavoriteMovie().value
        com.nhaarman.mockitokotlin2.verify (movieRepository).getFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)
        viewModel.getFavoriteMovie().observeForever(observer)
        com.nhaarman.mockitokotlin2.verify (observer).onChanged(dummyFavoriteMovie)
    }
}