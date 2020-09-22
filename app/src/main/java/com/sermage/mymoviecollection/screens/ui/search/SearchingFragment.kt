package com.sermage.mymoviecollection.screens.ui.search

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter


class SearchingFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    val searchableViewModel:SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        movieAdapter= MovieAdapter()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.main_menu,menu)
//        setupSearchView(menu.findItem(R.id.item_search).actionView as SearchView)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_searching, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewSearch = view.findViewById<RecyclerView>(R.id.recyclerViewSearchable)
        val searchView=view.findViewById<SearchView>(R.id.searchView)
        searchView.isFocusable = true
        searchView.isIconified = false
        recyclerViewSearch.adapter = movieAdapter
        recyclerViewSearch.layoutManager = GridLayoutManager(context, getColumnCount())
        searchableViewModel.getSearchableMovies().observe(viewLifecycleOwner, {
            movieAdapter.movies = it.toMutableList()
        })
        movieAdapter.posterListener=object:MovieAdapter.OnClickMoviePosterListener{
            override fun onClickMoviePoster(position: Int) {
                val movie=movieAdapter.movies[position]
                val bundle= bundleOf("movie" to movie)
                view.findNavController().navigate(R.id.action_searchingFragment_to_movieDetailsFragment,bundle)
            }

        }
        searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchableViewModel.loadData(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    movieAdapter.clear()
                    searchableViewModel.loadData(newText)
                }
                return true
            }

        })

    }

//    private fun setupSearchView(searchView: SearchView){
//        searchView.queryHint=getString(R.string.hint_search)
//        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    searchableViewModel.loadData(it)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let {
//                    movieAdapter.clear()
//                    searchableViewModel.loadData(newText)
//                }
//                return true
//            }
//
//        })
//    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 115 > 2) width / 115 else 2
    }


}

