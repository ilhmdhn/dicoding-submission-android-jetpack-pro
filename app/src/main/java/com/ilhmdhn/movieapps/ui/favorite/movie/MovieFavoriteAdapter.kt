package com.ilhmdhn.movieapps.ui.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ilhmdhn.movieapps.R
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.databinding.ListMoviesBinding
import com.ilhmdhn.movieapps.ui.detail.movie.DetailMovieActivity

class MovieFavoriteAdapter : PagedListAdapter<MovieEntity, MovieFavoriteAdapter.MovieFavoriteViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id   == newItem.id
            }
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteAdapter.MovieFavoriteViewHolder {
        val listMovieBinding = ListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavoriteViewHolder(listMovieBinding)
    }

    override fun onBindViewHolder(holderFavorite: MovieFavoriteAdapter.MovieFavoriteViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null){
            holderFavorite.bind(movie)
        }
    }


    class MovieFavoriteViewHolder(private val binding: ListMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntity: MovieEntity) {
            with(binding) {
                tvMovieTitle.text = movieEntity.title
                tvMoviesSynopsis.text = movieEntity.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500" + movieEntity.posterPath)
                    .transform(RoundedCorners(20))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgMovies)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, movieEntity.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}