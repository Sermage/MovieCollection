package com.sermage.mymoviecollection.screens.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import com.sermage.mymoviecollection.adapters.TVShowAdapter
import com.sermage.mymoviecollection.screens.ui.favorites.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_movie_details.*

/**
 * A fragment representing a list of Items.
 */

class FavoriteItemFragment : Fragment() {

    private var initNumber=0
    private lateinit var recyclerViewFavorites:RecyclerView
    private lateinit var favoriteViewModel:FavoritesViewModel


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
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        favoriteViewModel= ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewFavorites=view.findViewById(R.id.recyclerViewFavorites)
        if(initNumber==1){
            val favoritesAdapter= MovieAdapter()
            recyclerViewFavorites.adapter=favoritesAdapter
            favoriteViewModel.favoritesMovieList.observe(viewLifecycleOwner,{
                favoritesAdapter.movies=it.toMutableList()
            })
        }else if(initNumber==2){
           val favoritesAdapter=TVShowAdapter()
            recyclerViewFavorites.adapter=favoritesAdapter
            favoriteViewModel.favoritesTVShowList.observe(viewLifecycleOwner,{
                favoritesAdapter.tvShows=it.toMutableList()
            })
        }

    }



    companion object {

        private const val ARG_INIT="number"

    }
}