package com.ilhmdhn.movieapps.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.vo.Resource

interface DataSource {

    fun getAllMovie(): LiveData<Resource<PagedList<ListMovieEntity>>>

    fun getAllTv(): LiveData<Resource<PagedList<ListTvShowEntity>>>

    fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>>

    fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)

    fun getTvFavorite(): LiveData<PagedList<TvShowEntity>>
}