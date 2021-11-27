package com.ilhmdhn.movieapps.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ilhmdhn.movieapps.data.source.local.LocalDataSource
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.data.source.remote.RemoteDataSource
import com.ilhmdhn.movieapps.utils.AppExecutors
import com.ilhmdhn.movieapps.utils.DataDummy
import com.ilhmdhn.movieapps.utils.DataDummy.getMovieData
import com.ilhmdhn.movieapps.utils.DataDummy.getTvShowData
import com.ilhmdhn.movieapps.utils.LiveDataTestUtil.getValue
import com.ilhmdhn.movieapps.utils.PagedListUtils.mockPagedList
import com.ilhmdhn.movieapps.vo.Resource
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.getMovieData()
    private val movieId = movieResponses[0].id
    private val detailMovieResponse = DataDummy.getMovieDetail(movieId)[0]


    private val tvShowResponse = DataDummy.getTvShowData()
    private val tvShowId = tvShowResponse[0].id
    private val detailTvShowResponse = DataDummy.getTvShowDetail(tvShowId)[0]

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ListMovieEntity>
        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovie()
        val dummyMovieEntities = Resource.success(mockPagedList(getMovieData()))
        verify(local).getAllMovie()
        assertNotNull(dummyMovieEntities.data)
        assertEquals(movieResponses.size, dummyMovieEntities.data?.size)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllTvShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ListTvShowEntity>
        `when`(local.getAllTvShow()).thenReturn(dataSourceFactory)
        movieRepository.getAllTv()
        val dummyTvEntities = Resource.success(mockPagedList(getTvShowData()))
        verify(local).getAllTvShow()
        assertNotNull(dummyTvEntities.data)
        assertEquals(tvShowResponse.size, dummyTvEntities.data?.size)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getFavoriteMovies(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovieFavorite()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteMovie()
        val dummyMovieEntity = Resource.success(mockPagedList(getMovieData()))
        verify(local).getMovieFavorite()
        assertNotNull(dummyMovieEntity.data)
        assertEquals(movieResponses.size, dummyMovieEntity.data?.size)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getFavortieTv(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShowFavorite()).thenReturn(dataSourceFactory)
        movieRepository.getTvFavorite()
        val dummyTvEntities = Resource.success(mockPagedList(getTvShowData()))
        verify(local).getTvShowFavorite()
        assertNotNull(dummyTvEntities.data)
        assertEquals(tvShowResponse.size, dummyTvEntities.data?.size)
    }

    @Test
    fun getMovieDetail() {
        val dummyMovieEntity = MutableLiveData<MovieEntity>()
        dummyMovieEntity.value = DataDummy.getMovieDetail(movieId)[0]
        `when`(local.getDetailMovie(movieId)).thenReturn(dummyMovieEntity)
        val detailMovieEntity = getValue(movieRepository.getMovieDetail(movieId))
        verify(local).getDetailMovie(movieId)
        assertNotNull(detailMovieEntity)

        assertEquals(detailMovieResponse.id, detailMovieEntity.data?.id)
        assertEquals(detailMovieResponse.title, detailMovieEntity.data?.title)
        assertEquals(detailMovieResponse.language, detailMovieEntity.data?.language)
        assertEquals(detailMovieResponse.overview, detailMovieEntity.data?.overview)
        assertEquals(detailMovieResponse.popularity, detailMovieEntity.data?.popularity)
        assertEquals(detailMovieResponse.voteAverage, detailMovieEntity.data?.voteAverage)
    }

    @Test
    fun getTvShowDetail() {
        val dummyTvEntity = MutableLiveData<TvShowEntity>()
        dummyTvEntity.value = DataDummy.getTvShowDetail(tvShowId)[0]
        `when`(local.getDetailTvShow(tvShowId)).thenReturn(dummyTvEntity)
        val detailTvEntity = getValue(movieRepository.getTvShowDetail(tvShowId))
        verify(local).getDetailTvShow(tvShowId)
        assertNotNull(detailTvEntity)

        assertEquals(detailTvShowResponse.id, detailTvEntity.data?.id)
        assertEquals(detailTvShowResponse.name, detailTvEntity.data?.name)
        assertEquals(detailTvShowResponse.overview, detailTvEntity.data?.overview)
        assertEquals(detailTvShowResponse.popularity, detailTvEntity.data?.popularity)
        assertEquals(detailTvShowResponse.voteAverage, detailTvEntity.data?.voteAverage)
    }

    @Test
    fun setFavoriteMovie(){
        movieRepository.setFavoriteMovie(DataDummy.getMovieDetail(movieId)[0],true)
        verify(local).setMovieFavorite(DataDummy.getMovieDetail(movieId)[0], true)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun setTvShowFavorite(){
        movieRepository.setFavoriteTvShow(DataDummy.getTvShowDetail(tvShowId)[0],true)
        verify(local).setTvShowFavorite(DataDummy.getTvShowDetail(movieId)[0], true)
        verifyNoMoreInteractions(local)
    }
}