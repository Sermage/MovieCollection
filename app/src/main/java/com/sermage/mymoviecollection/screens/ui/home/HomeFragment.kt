package com.sermage.mymoviecollection.screens.ui.home

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.MovieAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private lateinit var movieAdapter:MovieAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var isLoading:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter= MovieAdapter()
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
        val recyclerViewMovies=view.findViewById<RecyclerView>(R.id.recyclerViewMoviePoster)
        recyclerViewMovies.layoutManager=GridLayoutManager(
            activity,
            getColumnCount()
        )
        recyclerViewMovies.adapter=movieAdapter
        movieAdapter.reachEndListener=object :MovieAdapter.OnReachEndListener{
            override fun onReachEnd() {
                if(!isLoading){
                    homeViewModel.loadData()
                }
            }
        }
        movieAdapter.posterListener=object :MovieAdapter.OnClickMoviePosterListener{
            override fun onClickMoviePoster(position: Int) {
                val movie=movieAdapter.movies[position]
                val bundle= bundleOf("movie" to movie)
                view.findNavController().navigate(R.id.action_navigation_home_to_movieDetailsFragment,bundle)
            }

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.loadData()
        homeViewModel.getMovies().observe(viewLifecycleOwner, {
            movieAdapter.movies = it
        })
        homeViewModel.getErrors().observe(viewLifecycleOwner, {
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        })
        homeViewModel.getStatusOfLoading().observe(viewLifecycleOwner,{
            setLoading(it)
        })
    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else 2
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
        if (isLoading) {
            progressBarLoading.visibility = View.VISIBLE
        } else {
            progressBarLoading.visibility = View.INVISIBLE
        }
    }


}