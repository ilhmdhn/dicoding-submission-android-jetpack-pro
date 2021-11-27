package com.ilhmdhn.movieapps.ui.detail.tvshow

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
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.databinding.ActivityTvShowDetailBinding
import com.ilhmdhn.movieapps.viewmodel.ViewModelFactory
import com.ilhmdhn.movieapps.vo.Status

class DetailTvShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_int"
    }

    private var _activityDetailTvShowBinding: ActivityTvShowDetailBinding? = null
    private val binding get() = _activityDetailTvShowBinding
    private lateinit var viewModel: DetailTvShowViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailTvShowBinding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val id = intent.extras?.getInt(EXTRA_ID)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]

        binding?.progressBar?.visibility = View.VISIBLE
        viewModel.setSelected(id!!)
        viewModel.detailTvShow.observe(this, { detailTvShowData ->
            if (detailTvShowData != null) {
                when (detailTvShowData.status) {
                    Status.LOADING -> binding?.progressBar?.visibility =
                        View.VISIBLE
                    Status.SUCCES -> if (detailTvShowData.data != null) {
                        binding?.progressBar?.visibility = View.GONE
                        postDetail(detailTvShowData.data)
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
        viewModel.detailTvShow.observe(this, { detailTvShowData ->
            if (detailTvShowData != null) {
                when (detailTvShowData.status) {
                    Status.LOADING -> binding?.progressBar?.visibility =
                        View.VISIBLE
                    Status.SUCCES -> if (detailTvShowData.data != null) {
                        binding?.progressBar?.visibility = View.GONE
                        val state = detailTvShowData.data.isFavorite
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
        return super.onContextItemSelected(item)
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

    private fun postDetail(show: TvShowEntity) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + show.posterPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding?.imgMovies!!)

        binding?.tvLanguage?.text = show.language
        binding?.tvTitle?.text = show.name
        binding?.tvSynopsis?.text = show.overview
        binding?.tvPopularity?.text = show.popularity.toString()
        binding?.tvRating?.text = show.voteAverage.toString()
    }
}