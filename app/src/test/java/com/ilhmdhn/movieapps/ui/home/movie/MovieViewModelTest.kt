package com.ilhmdhn.movieapps.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
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
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<ListMovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<ListMovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie = Resource.success(pagedList)
        `when`(dummyMovie.data?.size).thenReturn(3)
        val movie = MutableLiveData<Resource<PagedList<ListMovieEntity>>>()
        movie.value = dummyMovie
        `when`(movieRepository.getAllMovie()).thenReturn(movie)
        val movieEntities = viewModel.getMovies().value?.data
        verify(movieRepository).getAllMovie()
        assertNotNull(movieEntities)
        assertEquals(3, movieEntities?.size)
        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}