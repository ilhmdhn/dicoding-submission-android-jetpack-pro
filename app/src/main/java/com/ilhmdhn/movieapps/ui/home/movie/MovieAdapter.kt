package com.ilhmdhn.movieapps.ui.home.movie

import android.content.Intent
import android.util.Log
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

class MovieAdapter : PagedListAdapter<ListMovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListMovieEntity>() {
            override fun areItemsTheSame(oldItem: ListMovieEntity, newItem: ListMovieEntity): Boolean {
                return oldItem.id  == newItem.id
            }
            override fun areContentsTheSame(oldItem: ListMovieEntity, newItem: ListMovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val listMovieBinding = ListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(listMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null)  holder.bind(movies)
    }


    class MovieViewHolder(private val binding: ListMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listMovieEntity: ListMovieEntity) {
            with(binding) {
                tvMovieTitle.text = listMovieEntity.title
                tvMoviesSynopsis.text = listMovieEntity.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500" + listMovieEntity.posterPath)
                    .transform(RoundedCorners(20))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgMovies)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, listMovieEntity.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}