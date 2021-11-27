package com.ilhmdhn.movieapps.ui.favorite.tvshow

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
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.databinding.ListTvShowBinding
import com.ilhmdhn.movieapps.ui.detail.movie.DetailMovieActivity
import com.ilhmdhn.movieapps.ui.detail.tvshow.DetailTvShowActivity
import com.ilhmdhn.movieapps.ui.home.tvshow.TvShowAdapter

class TvShowFavoriteAdapter : PagedListAdapter<TvShowEntity, TvShowFavoriteAdapter.TvShowFavoriteViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id   == newItem.id
            }
            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): TvShowFavoriteAdapter.TvShowFavoriteViewHolder {
        val listTvShowBinding =
            ListTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowFavoriteViewHolder(listTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowFavoriteAdapter.TvShowFavoriteViewHolder,position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null){
        holder.bind(tvShow)
        }
    }

    class TvShowFavoriteViewHolder(private val binding: ListTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowEntity: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShowEntity.name
                tvSynopsis.text = tvShowEntity.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500" + tvShowEntity.posterPath)
                    .transform(RoundedCorners(20))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgTvShow)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvShowActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, tvShowEntity.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
