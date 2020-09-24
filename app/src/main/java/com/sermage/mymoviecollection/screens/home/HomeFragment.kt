package com.sermage.mymoviecollection.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import com.sermage.mymoviecollection.adapters.TVShowAdapter
import com.sermage.mymoviecollection.screens.moviedetails.MovieDetailsFragment
import com.sermage.mymoviecollection.screens.tvshowdetails.TVShowDetailsFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var movieAdapter1: MovieAdapter
    private lateinit var tvAdapter1: TVShowAdapter
    private lateinit var movieAdapter2: MovieAdapter
    private lateinit var tvAdapter2: TVShowAdapter
    private lateinit var movieAdapter3: MovieAdapter
    private var isLoading: Boolean = false
    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter1 = MovieAdapter()
        tvAdapter1 = TVShowAdapter()
        movieAdapter2 = MovieAdapter()
        tvAdapter2 = TVShowAdapter()
        movieAdapter3 = MovieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewTrendingMovies =
            view.findViewById<RecyclerView>(R.id.recyclerViewTrendingMovies)
        val recyclerViewTrendingTVshows =
            view.findViewById<RecyclerView>(R.id.recyclerViewTrendingTVshows)
        val recyclerViewRatedMovies = view.findViewById<RecyclerView>(R.id.recyclerViewRatedMovies)
        val recyclerViewRatedTVShows =
            view.findViewById<RecyclerView>(R.id.recyclerViewRatedTvShows)
        val recyclerViewKidMovies = view.findViewById<RecyclerView>(R.id.recyclerViewKidMovies)

        val headLineTrendingMovies = view.findViewById<View>(R.id.headLineTrendingMovies)
        val headLineTrendingTVshows = view.findViewById<View>(R.id.headLineTrendingTVshows)
        val headLineRatedMovies = view.findViewById<View>(R.id.headLineRatedMovies)
        val headLineRatedTVShows = view.findViewById<View>(R.id.headLineRatedTVShows)
        val headLineKidMovies = view.findViewById<View>(R.id.headLineKidMovies)

        val textViewLabel1 = headLineTrendingMovies.findViewById<TextView>(R.id.textViewLabel1)
        val textViewLabel2 = headLineTrendingMovies.findViewById<TextView>(R.id.textViewLabel2)
        val textViewLabelTV1 = headLineTrendingTVshows.findViewById<TextView>(R.id.textViewLabel1)
        val textViewLabelTV2 = headLineTrendingTVshows.findViewById<TextView>(R.id.textViewLabel2)
        val textViewLabel1RatedMovies =
            headLineRatedMovies.findViewById<TextView>(R.id.textViewLabel1)
        val textViewLabe2RatedMovies =
            headLineRatedMovies.findViewById<TextView>(R.id.textViewLabel2)
        val textViewLabel1RatedTVShows =
            headLineRatedTVShows.findViewById<TextView>(R.id.textViewLabel1)
        val textViewLabe2RatedTvShows =
            headLineRatedTVShows.findViewById<TextView>(R.id.textViewLabel2)
        val textViewLabel1KidMovies = headLineKidMovies.findViewById<TextView>(R.id.textViewLabel1)
        val textViewLabe2KidMovies = headLineKidMovies.findViewById<TextView>(R.id.textViewLabel2)

        textViewLabel1.text = getString(R.string.Tranding)
        textViewLabel2.text = getString(R.string.movies)
        textViewLabelTV1.text = getString(R.string.Tranding)
        textViewLabelTV2.text = getString(R.string.tv_shows)
        textViewLabel1RatedMovies.text = getString(R.string.top_rated)
        textViewLabe2RatedMovies.text = getString(R.string.movies)
        textViewLabel1RatedTVShows.text = getString(R.string.top_rated)
        textViewLabe2RatedTvShows.text = getString(R.string.tv_shows)
        textViewLabel1KidMovies.text = getString(R.string.for_kids)
        textViewLabe2KidMovies.text = getString(R.string.movies)

        recyclerViewTrendingMovies.adapter = movieAdapter1
        movieAdapter1.reachEndListener = object : MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                if (!isLoading) {
                    homeViewModel.loadTrendingMovies()
                }
            }
        }
        movieAdapter1.posterListener = object : MovieAdapter.OnClickMoviePosterListener {
            override fun onClickMoviePoster(position: Int) {
                val movie = movieAdapter1.movies[position]
                view.findNavController().navigate(
                    R.id.action_navigation_home_to_movieDetailsFragment,
                    MovieDetailsFragment.newInstance(movie).arguments
                )
            }

        }
        recyclerViewTrendingTVshows.adapter = tvAdapter1
        tvAdapter1.reachEndListener = object : TVShowAdapter.OnReachEndListener {
            override fun onReachEnd() {
                if (!isLoading) {
                    homeViewModel.loadTrendingTVShows()
                }
            }
        }
        tvAdapter1.posterListener = object : TVShowAdapter.OnClickTVShowPosterListener {
            override fun onClickTVShowPoster(position: Int) {
                val tvShow = tvAdapter1.tvShows[position]
                view.findNavController().navigate(
                    R.id.action_navigation_home_to_TVShowDetailsFragment,
                    TVShowDetailsFragment.newInstance(tvShow).arguments
                )
            }

        }

        recyclerViewRatedMovies.adapter = movieAdapter2
        movieAdapter2.reachEndListener = object : MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                if (!isLoading) {
                    homeViewModel.loadRatedMovies()
                }
            }
        }
        movieAdapter2.posterListener = object : MovieAdapter.OnClickMoviePosterListener {
            override fun onClickMoviePoster(position: Int) {
                val movie = movieAdapter2.movies[position]
                view.findNavController().navigate(
                    R.id.action_navigation_home_to_movieDetailsFragment,
                    MovieDetailsFragment.newInstance(movie).arguments
                )
            }
        }

        recyclerViewRatedTVShows.adapter = tvAdapter2
        tvAdapter2.reachEndListener = object : TVShowAdapter.OnReachEndListener {
            override fun onReachEnd() {
                if (!isLoading) {
                    homeViewModel.loadRatedTVShows()
                }
            }
        }
        tvAdapter2.posterListener = object : TVShowAdapter.OnClickTVShowPosterListener {
            override fun onClickTVShowPoster(position: Int) {
                val tvShow = tvAdapter2.tvShows[position]
                view.findNavController().navigate(
                    R.id.action_navigation_home_to_TVShowDetailsFragment,
                    TVShowDetailsFragment.newInstance(tvShow).arguments
                )
            }

        }
        recyclerViewKidMovies.adapter = movieAdapter3
        movieAdapter3.reachEndListener = object : MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                if (!isLoading) {
                    homeViewModel.loadKidMovies()
                }
            }
        }
        movieAdapter3.posterListener = object : MovieAdapter.OnClickMoviePosterListener {
            override fun onClickMoviePoster(position: Int) {
                val movie = movieAdapter3.movies[position]
                view.findNavController().navigate(
                    R.id.action_navigation_home_to_movieDetailsFragment,
                    MovieDetailsFragment.newInstance(movie).arguments
                )
            }

        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.getTrendingMovies().observe(viewLifecycleOwner, {
            movieAdapter1.movies = it.toMutableList()
        })

        homeViewModel.getTrendingTVShows().observe(viewLifecycleOwner, {
            tvAdapter1.tvShows = it.toMutableList()
        })
        homeViewModel.getErrors().observe(viewLifecycleOwner, {
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        })
        homeViewModel.getStatusOfLoading().observe(viewLifecycleOwner, {
            setLoading(it)
        })
        homeViewModel.getRatedMovies().observe(viewLifecycleOwner, {
            movieAdapter2.movies = it.toMutableList()
        })
        homeViewModel.getRatedTVShows().observe(viewLifecycleOwner, {
            tvAdapter2.tvShows = it.toMutableList()
        })
        homeViewModel.getKidMovies().observe(viewLifecycleOwner, {
            movieAdapter3.movies = it.toMutableList()
        })


    }


    private fun setLoading(loading: Boolean) {
        isLoading = loading
        if (isLoading) {
            progressBarLoading.visibility = View.VISIBLE
        } else {
            progressBarLoading.visibility = View.INVISIBLE
        }
    }


}