package com.ilhmdhn.movieapps.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.movieapps.databinding.FragmentTvShowFavoriteBinding
import com.ilhmdhn.movieapps.viewmodel.ViewModelFactory

class TvShowFavoriteFragment : Fragment() {

    private var _fragmentTvShowFavoriteBinding: FragmentTvShowFavoriteBinding? = null
    private val binding get() = _fragmentTvShowFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowFavoriteBinding =
            FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowFavoriteViewModel::class.java]
            val tvShowFavoriteAdapter = TvShowFavoriteAdapter()

            binding?.progressBar?.visibility = View.VISIBLE

            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner, { tvShow ->
                if (tvShow != null) {
                    binding?.progressBar?.visibility = View.GONE
                    tvShowFavoriteAdapter.submitList(tvShow)
                }
            })
            with(binding?.rvTvShow) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = tvShowFavoriteAdapter
            }
        }
    }
}