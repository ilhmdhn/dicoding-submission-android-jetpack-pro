package com.ilhmdhn.movieapps.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilhmdhn.movieapps.R
import com.ilhmdhn.movieapps.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val sectionsPagerAdapter = SectionsPagerAdapterFavorite(this, supportFragmentManager)
        activityFavoriteBinding.viewPager.adapter = sectionsPagerAdapter
        activityFavoriteBinding.tabs.setupWithViewPager(activityFavoriteBinding.viewPager)

        supportActionBar?.title = getString(R.string.favorite_title)
    }
}