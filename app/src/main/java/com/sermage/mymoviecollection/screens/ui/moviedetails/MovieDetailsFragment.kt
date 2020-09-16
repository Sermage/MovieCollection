package com.sermage.mymoviecollection.screens.ui.moviedetails

import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.adapters.ReviewAdapter
import com.sermage.mymoviecollection.adapters.TrailerAdapter
import com.sermage.mymoviecollection.pojo.Movie
import com.sermage.mymoviecollection.screens.MainActivity
import com.sermage.mymoviecollection.screens.ui.favorites.FavoritesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_MOVIE = "movie"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/"
    private val BIG_POSTER_SIZE = "w500"
    private var movie:Movie?=null
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var trailerAdapter: TrailerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie=it.get(ARG_MOVIE) as Movie
        }
        requireActivity().actionBar?.title=movie?.title
        reviewAdapter= ReviewAdapter()
        trailerAdapter= TrailerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesViewModel:FavoritesViewModel by viewModels()
        val movieDetailsViewModel:MovieDetailsViewModel by viewModels()

        val imageViewBigPoster: ImageView = view.findViewById(R.id.imageViewBigPoster)
        val textViewTitle: TextView =view.findViewById(R.id.textViewTitle)
        val textViewOriginalTitle: TextView =view.findViewById(R.id.textViewOriginalTitle)
        val textViewRating: TextView = view.findViewById(R.id.textViewRating)
        val textViewReleaseDate: TextView =view.findViewById(R.id.textViewReleaseDate)
        val textViewOverview: TextView =view.findViewById(R.id.textViewOverview)
        val imageViewFavourite:ImageView =view.findViewById(R.id.imageViewFavourite)
        val recyclerViewReviews: RecyclerView =view.findViewById(R.id.recyclerViewReviews)
        val recyclerViewTrailers: RecyclerView =view.findViewById(R.id.recyclerViewTrailers)

        Picasso.get().load(IMAGE_PATH+BIG_POSTER_SIZE+movie?.posterPath).placeholder(R.drawable.waiting_for_loading_poster).into(imageViewBigPoster)
        textViewTitle.text=movie?.title
        textViewOriginalTitle.text=movie?.originalTitle
        textViewOverview.text=movie?.overview
        textViewRating.text=movie?.voteAverage.toString()
        textViewReleaseDate.text=formatReleaseDate(movie?.releaseDate)
        if(movie?.isFavorite==true){
            imageViewFavourite.setImageResource(R.drawable.ic_star_favourite_60dp)
        }else{
            imageViewFavourite.setImageResource(R.drawable.ic_star_grey_60dp)
        }

        imageViewFavourite.setOnClickListener {
            if(movie?.isFavorite==false){
                movie?.let { it1 -> favoritesViewModel.insertMovieToFavorites(it1) }
                movie?.isFavorite=true
                Toast.makeText(context, R.string.added_to_favourites, Toast.LENGTH_SHORT).show()
            }else{
                movie?.let{
                    favoritesViewModel.deleteMovieFromFavorites(it)
                    movie?.isFavorite=false
                    Toast.makeText(context, R.string.deleted_from_favourites, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            setFavourite()
        }
        //Присоединяем отзывы
        recyclerViewReviews.adapter=reviewAdapter
        movie?.id?.let { movieDetailsViewModel.loadReviews(it) }
        movieDetailsViewModel.getReviews().observe(viewLifecycleOwner,{
            reviewAdapter.reviews=it
        })
        //Присоединяем трейлеры

        recyclerViewTrailers.adapter=trailerAdapter
        movie?.id?.let { movieDetailsViewModel.loadTrailers(it) }
        movieDetailsViewModel.getTrailers().observe(viewLifecycleOwner,{
            trailerAdapter.trailers=it
        })
        trailerAdapter.onTrailerClickListener=object :TrailerAdapter.OnTrailerClickListener{
            override fun onTrailerClick(url: String?) {
                val intent= Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }

        }
    }

    private fun setFavourite() {
         val isFavorite=movie?.isFavorite
        if (isFavorite==false) {
            imageViewFavourite.setImageResource(R.drawable.ic_star_grey_60dp)
        } else {
            imageViewFavourite.setImageResource(R.drawable.ic_star_favourite_60dp)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(movie:Movie) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                   putSerializable(ARG_MOVIE,movie)
                }
            }

    }

    private fun formatReleaseDate(releaseDate: String?):String? {
        releaseDate?.let {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("LLLL yyyy", Locale.getDefault())
            val date: Date?
            var result: String? = null
            try {
                date = inputDateFormat.parse(it)
                if (date != null) {
                    result = outputDateFormat.format(date)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
           return result
        }
        return null
    }




}