package com.ilhmdhn.movieapps.ui.detail.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.vo.Resource

class DetailTvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private var id = MutableLiveData<Int>()

    fun setSelected(id: Int) {
        this.id.value = id
    }

    var detailTvShow: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(id) {
        movieRepository.getTvShowDetail(it)
    }

    fun setFavorite() {
        val detailTvShowResource = detailTvShow.value
        if (detailTvShowResource != null) {
            val detailTvShow = detailTvShowResource.data
            if (detailTvShow != null) {
                val newState = !detailTvShow.isFavorite
                movieRepository.setFavoriteTvShow(detailTvShow, newState)
            }
        }
    }
}