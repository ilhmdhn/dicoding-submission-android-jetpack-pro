package com.ilhmdhn.movieapps.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllMovie(): DataSource.Factory<Int, ListMovieEntity> = mMovieDao.getMovies()

    fun getMovieFavorite(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getMovieFavorite()

    fun getDetailMovie(movieId: Int): LiveData<MovieEntity> = mMovieDao.getDetailMovie(movieId)

    fun insertListMovie(movie: List<ListMovieEntity>) = mMovieDao.insertMovie(movie)

    fun insertDetailMovie(detailMovie: MovieEntity) =
        mMovieDao.insertDetailMovie(detailMovie)


    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mMovieDao.updateMovie(movie)
    }

    fun getAllTvShow(): DataSource.Factory<Int, ListTvShowEntity> = mMovieDao.getTvShow()

    fun getTvShowFavorite(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getTvShowFavorite()

    fun getDetailTvShow(tvShowId: Int): LiveData<TvShowEntity> = mMovieDao.getDetailTvShow(tvShowId)

    fun insertListTvShow(tvShow: List<ListTvShowEntity>) = mMovieDao.insertTvShow(tvShow)

    fun insertDetailTvShow(detailTvShow: TvShowEntity) =
        mMovieDao.insertDetailTvShow(detailTvShow)

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        mMovieDao.updateTvShow(tvShow)
    }
}