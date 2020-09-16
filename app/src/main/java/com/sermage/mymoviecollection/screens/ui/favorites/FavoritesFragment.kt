package com.sermage.mymoviecollection.screens.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.screens.ui.moviedetails.MovieDetailsFragment
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter


class FavoritesFragment : Fragment() {


    private lateinit var favoritesAdapter:MovieAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewFavorites=view.findViewById<RecyclerView>(R.id.recyclerViewFavorites)
        recyclerViewFavorites.layoutManager=GridLayoutManager(context,2)
        favoritesAdapter= MovieAdapter()
        recyclerViewFavorites.adapter=favoritesAdapter
        favoritesAdapter.posterListener=object :MovieAdapter.OnClickMoviePosterListener{
            override fun onClickMoviePoster(position: Int) {
                val movie=favoritesAdapter.movies[position]
                view.findNavController().navigate(R.id.action_navigation_favorites_to_movieDetailsFragment,
                    MovieDetailsFragment.newInstance(movie).arguments)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val favoritesViewModel:FavoritesViewModel by viewModels()
        favoritesViewModel.favoritesList.observe(viewLifecycleOwner, Observer {
            favoritesAdapter.movies=it.toMutableList()
        })
    }
}