package com.sermage.mymoviecollection.screens.search

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
import com.sermage.mymoviecollection.screens.AppActivity
import com.sermage.mymoviecollection.screens.moviedetails.MovieDetailsFragment


class GenreFragment : Fragment() {

    private var movieGenre:String?=null
    private var title: String? = null
    private lateinit var movieAdapter:MovieAdapter
    private val searchViewModel:SearchViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieGenre = it.getString(ARG_MOVIE_GENRE)
            title = it.getString(ARG_TITLE)
        }
        val appActivity = activity as AppActivity
        appActivity.supportActionBar?.title = title
        movieAdapter= MovieAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewMoviewByGenre=view.findViewById<RecyclerView>(R.id.recyclerViewMoviesByGenre)
        recyclerViewMoviewByGenre.layoutManager=GridLayoutManager(context, getColumnCount())
        recyclerViewMoviewByGenre.adapter=movieAdapter
        searchViewModel.getMoviesByGenre().observe(viewLifecycleOwner,{
            movieAdapter.movies=it.toMutableList()
        })
        movieGenre?.let { searchViewModel.loadMoviesByGenre(it) }
        movieAdapter.posterListener=object :MovieAdapter.OnClickMoviePosterListener{
            override fun onClickMoviePoster(position: Int) {
                val movie = movieAdapter.movies[position]
                view.findNavController().navigate(
                    R.id.action_genreFragment_to_movieDetailsFragment,
                    MovieDetailsFragment.newInstance(movie).arguments
                )
            }
        }
        movieAdapter.reachEndListener=object :MovieAdapter.OnReachEndListener{
            override fun onReachEnd() {
                movieGenre?.let { searchViewModel.loadMoviesByGenre(it)}
            }

        }
    }


    companion object {
        @JvmStatic
        fun newInstance(movie_genre: String, title: String) =
            GenreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MOVIE_GENRE, movie_genre)
                    putString(ARG_TITLE, title)
                }
            }

        private const val ARG_MOVIE_GENRE = "movie_genre"
        private const val ARG_TITLE = "title"

    }

    private fun getColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 115 > 2) width / 115 else 2
    }

    override fun onStop() {
        super.onStop()
        val appActivity = activity as AppActivity
        appActivity.supportActionBar?.title =""
    }
}