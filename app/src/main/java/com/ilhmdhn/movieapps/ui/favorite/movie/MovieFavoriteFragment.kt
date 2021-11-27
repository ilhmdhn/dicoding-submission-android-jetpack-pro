package com.ilhmdhn.movieapps.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.movieapps.databinding.FragmentMovieFavoriteBinding
import com.ilhmdhn.movieapps.viewmodel.ViewModelFactory

class MovieFavoriteFragment : Fragment() {

    private var _fragmentMovieFavoriteBinding: FragmentMovieFavoriteBinding? = null
    private val binding get() = _fragmentMovieFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieFavoriteBinding =
            FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieFavoriteViewModel::class.java]
            val movieFavoriteAdapter = MovieFavoriteAdapter()

            binding?.progressBar?.visibility = View.VISIBLE

            viewModel.getFavoriteMovie().observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    binding?.progressBar?.visibility = View.GONE
                    movieFavoriteAdapter.submitList(movie)
                }
            })
            with(binding?.rvMovieFavorite) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = movieFavoriteAdapter
            }
        }
    }
}