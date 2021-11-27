package com.ilhmdhn.movieapps.ui.detail.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilhmdhn.movieapps.R
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.databinding.ActivityMovieDetailBinding
import com.ilhmdhn.movieapps.viewmodel.ViewModelFactory
import com.ilhmdhn.movieapps.vo.Status

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_int"
    }

    private var _activityDetailMovieBinding: ActivityMovieDetailBinding? = null
    private val binding get() = _activityDetailMovieBinding

    private lateinit var viewModel: DetailMovieViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailMovieBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val id = intent.extras?.getInt(EXTRA_ID)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        viewModel.setSelected(id!!)
        viewModel.detailMovie.observe(this, { detailMovieData ->
            if (detailMovieData != null) {
                when (detailMovieData.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> if (detailMovieData.data != null) {
                        binding?.progressBar?.visibility = View.GONE
                        postDetail(detailMovieData.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.detailMovie.observe(this, { detailMovieData ->
            if (detailMovieData != null) {
                when (detailMovieData.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> if (detailMovieData.data != null) {
                        binding?.progressBar?.visibility = View.GONE
                        val state = detailMovieData.data.isFavorite
                        setFavoriteState(state)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.setFavorite()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited_white)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
        }
    }

    private fun postDetail(show: MovieEntity) {
        supportActionBar?.title = show.title
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + show.posterPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding?.imgMovies!!)

        with(binding) {
            this?.tvLanguage?.text = show.language
            this?.tvTitle?.text = show.title
            this?.tvSynopsis?.text = show.overview
            this?.tvPopularity?.text = show.popularity.toString()
            this?.tvRating?.text = show.voteAverage.toString()
        }
    }
}