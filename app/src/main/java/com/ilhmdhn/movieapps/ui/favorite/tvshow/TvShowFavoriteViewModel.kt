package com.ilhmdhn.movieapps.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity

class TvShowFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> = movieRepository.getTvFavorite()
}