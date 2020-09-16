package com.sermage.mymoviecollection.screens.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import com.sermage.mymoviecollection.screens.ui.moviedetails.MovieDetailsFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private lateinit var movieAdapter:MovieAdapter
    private lateinit var tvAdapter:MovieAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var isLoading:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter= MovieAdapter()
        tvAdapter=MovieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewTrendingMovies=view.findViewById<RecyclerView>(R.id.recyclerViewTrendingMovies)
        val recyclerViewTrendingTVshows=view.findViewById<RecyclerView>(R.id.recyclerViewTrendingTVshows)
        val headLineTrendingMovies=view.findViewById<View>(R.id.headLineTrendingMovies)
        val headLineTrendingTVshows=view.findViewById<View>(R.id.headLineTrendingTVshows)
        val textViewLabel1=headLineTrendingMovies.findViewById<TextView>(R.id.textViewLabel1)
        val textViewLabel2=headLineTrendingMovies.findViewById<TextView>(R.id.textViewLabel2)
        val textViewLabelTV1=headLineTrendingTVshows.findViewById<TextView>(R.id.textViewLabel1)
        val textViewLabelTV2=headLineTrendingTVshows.findViewById<TextView>(R.id.textViewLabel2)
        textViewLabel1.text=getString(R.string.Tranding)
        textViewLabel2.text=getString(R.string.movies)
        textViewLabelTV1.text=getString(R.string.Tranding)
        textViewLabelTV2.text=getString(R.string.tv_shohs)

        recyclerViewTrendingMovies.adapter=movieAdapter
        movieAdapter.reachEndListener=object :MovieAdapter.OnReachEndListener{
            override fun onReachEnd() {
                if(!isLoading){
                    homeViewModel.loadTrendingMovies()
                }
            }
        }
        movieAdapter.posterListener=object :MovieAdapter.OnClickMoviePosterListener{
            override fun onClickMoviePoster(position: Int) {
                val movie=movieAdapter.movies[position]
                view.findNavController().navigate(R.id.action_navigation_home_to_movieDetailsFragment,MovieDetailsFragment.newInstance(movie).arguments)
            }

        }
        recyclerViewTrendingTVshows.adapter=tvAdapter
        tvAdapter.reachEndListener=object :MovieAdapter.OnReachEndListener{
            override fun onReachEnd() {
                if(!isLoading){
                    homeViewModel.loadTrendingTVShows()
                }
            }
        }
        tvAdapter.posterListener=object :MovieAdapter.OnClickMoviePosterListener{
            override fun onClickMoviePoster(position: Int) {
                val movie=tvAdapter.movies[position]
                view.findNavController().navigate(R.id.action_navigation_home_to_movieDetailsFragment,MovieDetailsFragment.newInstance(movie).arguments)
            }

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.loadTrendingMovies()
        homeViewModel.getTrendingMovies().observe(viewLifecycleOwner, {
            movieAdapter.movies = it.toMutableList()
        })

        homeViewModel.loadTrendingTVShows()
        homeViewModel.getTrendingTVShows().observe(viewLifecycleOwner,{
            tvAdapter.movies=it.toMutableList()
        })
        homeViewModel.getErrors().observe(viewLifecycleOwner, {
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        })
        homeViewModel.getStatusOfLoading().observe(viewLifecycleOwner,{
            setLoading(it)
        })


    }

//        private fun getColumnCount(): Int {
//        val displayMetrics = DisplayMetrics()
//        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
//        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
//        return if (width / 185 > 2) width / 185 else 2
//    }

    private fun setLoading(loading: Boolean) {
        isLoading = loading
        if (isLoading) {
            progressBarLoading.visibility = View.VISIBLE
        } else {
            progressBarLoading.visibility = View.INVISIBLE
        }
    }





}