package com.ilhmdhn.movieapps.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.di.Injection
import com.ilhmdhn.movieapps.ui.detail.movie.DetailMovieViewModel
import com.ilhmdhn.movieapps.ui.detail.tvshow.DetailTvShowViewModel
import com.ilhmdhn.movieapps.ui.favorite.movie.MovieFavoriteViewModel
import com.ilhmdhn.movieapps.ui.favorite.tvshow.TvShowFavoriteViewModel
import com.ilhmdhn.movieapps.ui.home.movie.MovieViewModel
import com.ilhmdhn.movieapps.ui.home.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mMoviewRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(
                mMoviewRepository
            ) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(
                mMoviewRepository
            ) as T
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(
                mMoviewRepository
            ) as T
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> DetailTvShowViewModel(
                mMoviewRepository
            ) as T
            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java) -> MovieFavoriteViewModel(
                mMoviewRepository
            ) as T
            modelClass.isAssignableFrom(TvShowFavoriteViewModel::class.java) -> TvShowFavoriteViewModel(
                mMoviewRepository
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}