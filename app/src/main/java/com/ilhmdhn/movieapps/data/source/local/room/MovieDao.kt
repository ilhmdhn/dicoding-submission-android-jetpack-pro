package com.ilhmdhn.movieapps.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM listmovieentities")
    fun getMovies(): DataSource.Factory<Int, ListMovieEntity>

    @Query("SELECT * FROM movieentities where is_favorite  = 1")
    fun getMovieFavorite(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<ListMovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentities WHERE id = :id")
    fun getDetailMovie(id: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMovie(detailMovie: MovieEntity)


    @Query("SELECT * FROM listtvshowentities")
    fun getTvShow(): DataSource.Factory<Int, ListTvShowEntity>

    @Query("SELECT * FROM tvshowentities where is_favorite = 1")
    fun getTvShowFavorite(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<ListTvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Query("SELECT * FROM tvshowentities WHERE id = :id")
    fun getDetailTvShow(id: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTvShow(detailTvShow: TvShowEntity)
}