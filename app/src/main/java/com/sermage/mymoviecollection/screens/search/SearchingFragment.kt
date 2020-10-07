package com.sermage.mymoviecollection.screens.search

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import com.sermage.mymoviecollection.adapters.TVShowAdapter
import com.sermage.mymoviecollection.screens.tvshowdetails.TVShowDetailsFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_searching.*


class SearchingFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvShowAdapter: TVShowAdapter
    private lateinit var recyclerViewSearch: RecyclerView
    private lateinit var tabLayout: TabLayout
    private val searchableViewModel: SearchViewModel by viewModels()
    private var isLoadingMovies = false
    private var isLoadingTVShows = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        movieAdapter = MovieAdapter()
        tvShowAdapter = TVShowAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.queryHint = getString(R.string.hint_search)
        //строка поиска открывается автоматически
        searchView.isIconified = false
        searchView.isFocusable = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchableViewModel.loadMovies(it)
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            searchableViewModel.loadMovies(it)
                            //слушатель пролистывания до конца страницы
                            movieAdapter.reachEndListener =
                                object : MovieAdapter.OnReachEndListener {
                                    override fun onReachEnd() {
                                        if(!isLoadingMovies) {
                                            searchableViewModel.loadMovies(it)
                                        }

                                    }

                                }
                        }
                        1 -> {
                            searchableViewModel.loadTvShows(it)
                            tvShowAdapter.reachEndListener =
                                object : TVShowAdapter.OnReachEndListener {
                                    override fun onReachEnd() {
                                        if(!isLoadingTVShows) {
                                            searchableViewModel.loadTvShows(it)
                                        }

                                    }

                                }
                        }
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    SearchViewModel.page=1
                    movieAdapter.clear()
                    searchableViewModel.getSearchingMoviesList().clear()
                    searchableViewModel.loadMovies(it)
                    when (tabLayout.selectedTabPosition) {
                        0 -> {
                            SearchViewModel.page=1
                            movieAdapter.clear()
                            searchableViewModel.getSearchingMoviesList().clear()
                            searchableViewModel.loadMovies(it)
                            movieAdapter.reachEndListener =
                                object : MovieAdapter.OnReachEndListener {
                                    override fun onReachEnd() {
                                        if(!isLoadingMovies) {
                                            searchableViewModel.loadMovies(it)
                                        }

                                    }

                                }
                        }
                        1 -> {
                            SearchViewModel.page=1
                            tvShowAdapter.clear()
                            searchableViewModel.getSearchingTVShowList().clear()
                            searchableViewModel.loadTvShows(it)
                            tvShowAdapter.reachEndListener =
                                object : TVShowAdapter.OnReachEndListener {
                                    override fun onReachEnd() {
                                        if(!isLoadingTVShows) {
                                            searchableViewModel.loadTvShows(it)
                                        }

                                    }

                                }
                        }
                    }
                }
                return true
            }

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_searching, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewSearch = view.findViewById(R.id.recyclerViewSearchable)
        recyclerViewSearch.layoutManager = GridLayoutManager(context, getColumnCount())
        searchingMovies(view)
        tabLayout = view.findViewById(R.id.tabLayoutForSearch)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> searchingMovies(view)
                    1 -> searchingTVShows(view)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun searchingMovies(view: View) {
        recyclerViewSearch.adapter = movieAdapter
        searchableViewModel.getSearchableMovies().observe(viewLifecycleOwner, {
            movieAdapter.movies = it.toMutableList()
        })
        movieAdapter.posterListener = object : MovieAdapter.OnClickMoviePosterListener {
            override fun onClickMoviePoster(position: Int) {
                val movie = movieAdapter.movies[position]
                val bundle = bundleOf("movie" to movie)
                view.findNavController()
                    .navigate(R.id.action_searchingFragment_to_movieDetailsFragment, bundle)
            }

        }
        searchableViewModel.getStatusOfLoadingMovies().observe(viewLifecycleOwner, {
            setLoadingMovies(it)
        })

    }

    private fun searchingTVShows(view: View) {
        recyclerViewSearch.adapter = tvShowAdapter
        searchableViewModel.getSearchableTvShows().observe(viewLifecycleOwner, {
            tvShowAdapter.tvShows = it.toMutableList()
        })
        tvShowAdapter.posterListener = object : TVShowAdapter.OnClickTVShowPosterListener {
            override fun onClickTVShowPoster(position: Int) {
                val tvShow = tvShowAdapter.tvShows[position]
                view.findNavController().navigate(
                    R.id.action_searchingFragment_to_TVShowDetailsFragment,
                    TVShowDetailsFragment.newInstance(tvShow).arguments
                )
            }

        }
        searchableViewModel.getStatusOfLoadingTV().observe(viewLifecycleOwner, {
            setLoadingTV(it)
        })
    }


    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 115 > 2) width / 115 else 2
    }

    private fun setLoadingMovies(loading: Boolean) {
        isLoadingMovies = loading
        if (isLoadingMovies) {
            progressBarSearchLoading.visibility = View.VISIBLE
        } else {
            progressBarSearchLoading.visibility = View.INVISIBLE
        }
    }

    private fun setLoadingTV(loading: Boolean) {
        isLoadingTVShows = loading
        if (isLoadingTVShows) {
            progressBarSearchLoading.visibility = View.VISIBLE
        } else {
            progressBarSearchLoading.visibility = View.INVISIBLE
        }
    }


}

