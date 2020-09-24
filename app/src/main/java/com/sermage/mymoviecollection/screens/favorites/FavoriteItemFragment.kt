package com.sermage.mymoviecollection.screens.favorites

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import com.sermage.mymoviecollection.adapters.TVShowAdapter
import com.sermage.mymoviecollection.screens.moviedetails.MovieDetailsFragment
import com.sermage.mymoviecollection.screens.tvshowdetails.TVShowDetailsFragment


class FavoriteItemFragment : Fragment() {

    private var initNumber = 0
    private lateinit var recyclerViewFavorites: RecyclerView
    private val favoriteViewModel: FavoritesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            initNumber = it.getInt(ARG_INIT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites)
        recyclerViewFavorites.layoutManager = GridLayoutManager(context, getColumnCount())
        if (initNumber == 1) {
            val favoritesAdapter = MovieAdapter()
            recyclerViewFavorites.adapter = favoritesAdapter
            favoriteViewModel.favoritesMovieList.observe(viewLifecycleOwner, {
                favoritesAdapter.movies = it.toMutableList()
            })
            favoritesAdapter.posterListener = object : MovieAdapter.OnClickMoviePosterListener {
                override fun onClickMoviePoster(position: Int) {
                    val movie = favoritesAdapter.movies[position]
                    view.findNavController().navigate(
                        R.id.action_navigation_favorites_to_movieDetailsFragment,
                        MovieDetailsFragment.newInstance(movie).arguments
                    )
                }

            }
        } else if (initNumber == 2) {
            val favoritesAdapter = TVShowAdapter()
            recyclerViewFavorites.adapter = favoritesAdapter
            favoriteViewModel.favoritesTVShowList.observe(viewLifecycleOwner, {
                favoritesAdapter.tvShows = it.toMutableList()
            })
            favoritesAdapter.posterListener = object : TVShowAdapter.OnClickTVShowPosterListener {
                override fun onClickTVShowPoster(position: Int) {
                    val tvShow = favoritesAdapter.tvShows[position]
                    view.findNavController().navigate(
                        R.id.action_navigation_favorites_to_TVShowDetailsFragment,
                        TVShowDetailsFragment.newInstance(tvShow).arguments
                    )
                }

            }
        }

    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 115 > 2) width / 115 else 2
    }

    companion object {

        private const val ARG_INIT = "number"

    }
}