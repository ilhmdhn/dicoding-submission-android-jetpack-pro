package com.ilhmdhn.movieapps.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.vo.Resource

class DetailMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private var movieId = MutableLiveData<Int>()

    fun setSelected(movieId: Int) {
        this.movieId.value = movieId
    }


    var detailMovie: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) {
        movieRepository.getMovieDetail(it)
    }

    fun setFavorite() {
        val detailMovieResource = detailMovie.value
        if (detailMovieResource != null) {
            val detailMovie = detailMovieResource.data
            if (detailMovie != null) {
                val newState = !detailMovie.isFavorite
                movieRepository.setFavoriteMovie(detailMovie, newState)
            }
        }
    }
}