package com.sermage.mymoviecollection.screens.ui.search

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import com.sermage.mymoviecollection.screens.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    private lateinit var movieAdapter:MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter= MovieAdapter()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchableViewModel:SearchViewModel by viewModels()
        val recyclerViewSearch=view.findViewById<RecyclerView>(R.id.recyclerViewSearchable)
        val searchView=view.findViewById<SearchView>(R.id.searchViewMovies)
        recyclerViewSearch.adapter=movieAdapter
        recyclerViewSearch.layoutManager=GridLayoutManager(context,getColumnCount())
        searchableViewModel.getSearchableMovies().observe(viewLifecycleOwner,{
            movieAdapter.movies=it.toMutableList()
        })
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
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

        movieAdapter.posterListener=object:MovieAdapter.OnClickMoviePosterListener{
            override fun onClickMoviePoster(position: Int) {
                val movie=movieAdapter.movies[position]
                val bundle= bundleOf("movie" to movie)
                view.findNavController().navigate(R.id.action_navigation_search_to_movieDetailsFragment,bundle)
            }

        }

    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else 2
    }
}