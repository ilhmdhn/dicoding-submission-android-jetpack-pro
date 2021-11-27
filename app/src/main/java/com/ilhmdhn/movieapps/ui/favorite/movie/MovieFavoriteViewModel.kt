package com.ilhmdhn.movieapps.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity

class MovieFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> = movieRepository.getFavoriteMovie()
}