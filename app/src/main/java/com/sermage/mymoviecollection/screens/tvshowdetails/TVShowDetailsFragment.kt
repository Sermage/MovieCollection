package com.sermage.mymoviecollection.screens.tvshowdetails

import android.content.Intent
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
import com.sermage.mymoviecollection.pojo.TVShow
import com.sermage.mymoviecollection.screens.AppActivity
import com.sermage.mymoviecollection.screens.favorites.FavoritesViewModel
import com.sermage.mymoviecollection.screens.moviedetails.MovieDetailsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class TVShowDetailsFragment : Fragment() {

    private lateinit var tvShow: TVShow
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var trailerAdapter: TrailerAdapter

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val tvShowViewModel: TvShowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tvShow = it.get(ARG_TVSHOW) as TVShow

        }
        val appActivity = activity as AppActivity
        appActivity.supportActionBar?.title = tvShow.name
        reviewAdapter= ReviewAdapter()
        trailerAdapter=TrailerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageViewBigPoster: ImageView = view.findViewById(R.id.imageViewBigPoster)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewOriginalTitle: TextView = view.findViewById(R.id.textViewOriginalTitle)
        val textViewRating: TextView = view.findViewById(R.id.textViewRating)
        val textViewFirstAirDate: TextView = view.findViewById(R.id.textViewFirstAirDate)
        val textViewOverview: TextView = view.findViewById(R.id.textViewOverview)
        val imageViewFavourite: ImageView = view.findViewById(R.id.imageViewFavourite)
        val recyclerViewReviews: RecyclerView = view.findViewById(R.id.recyclerViewReviews)
        val recyclerViewTrailers: RecyclerView = view.findViewById(R.id.recyclerViewTrailers)

        Picasso.get().load(IMAGE_PATH + BIG_POSTER_SIZE + tvShow.posterPath)
            .placeholder(R.drawable.waiting_for_loading_poster).into(imageViewBigPoster)
        textViewTitle.text = tvShow.name
        textViewOriginalTitle.text = tvShow.originalName
        textViewOverview.text = tvShow.overview
        textViewRating.text = tvShow.voteAverage.toString()
        textViewFirstAirDate.text = formatReleaseDate(tvShow.firstAirDate)
        if (tvShow.isFavorite == true) {
            imageViewFavourite.setImageResource(R.drawable.ic_star_favourite_60dp)
        } else {
            imageViewFavourite.setImageResource(R.drawable.ic_star_grey_60dp)
        }

        imageViewFavourite.setOnClickListener {
            if (tvShow.isFavorite == false) {
                tvShow.let { it1 -> favoritesViewModel.insertTVShowToFavorites(it1) }
                tvShow.isFavorite = true
                Toast.makeText(context, R.string.added_to_favourites, Toast.LENGTH_SHORT).show()
            } else {
                tvShow.let {
                    favoritesViewModel.deleteTvShowFromFavorites(it)
                    tvShow.isFavorite = false
                    Toast.makeText(context, R.string.deleted_from_favourites, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            setFavourite()
        }
        //Присоединяем отзывы
        recyclerViewReviews.adapter = reviewAdapter
        tvShow.id.let {tvShowViewModel.loadTVShowReviews(it) }
        tvShowViewModel.getReviews().observe(viewLifecycleOwner, {
            reviewAdapter.reviews = it
        })
        //Присоединяем трейлеры
        recyclerViewTrailers.adapter = trailerAdapter
        tvShow.id.let { tvShowViewModel.loadTVShowTrailers(it) }
        tvShowViewModel.getTrailers().observe(viewLifecycleOwner, {
            trailerAdapter.trailers = it
        })
        trailerAdapter.onTrailerClickListener = object : TrailerAdapter.OnTrailerClickListener {
            override fun onTrailerClick(url: String?) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }

        }
    }


    private fun setFavourite() {
        val isFavorite = tvShow.isFavorite
        if (isFavorite == false) {
            imageViewFavourite.setImageResource(R.drawable.ic_star_grey_60dp)
        } else {
            imageViewFavourite.setImageResource(R.drawable.ic_star_favourite_60dp)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tvShow: TVShow) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TVSHOW, tvShow)
                }
            }
        private const val ARG_TVSHOW = "tvshow"
        private const val IMAGE_PATH = "https://image.tmdb.org/t/p/"
        private const val BIG_POSTER_SIZE = "w500"
    }

    private fun formatReleaseDate(releaseDate: String?): String? {
        releaseDate?.let {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
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

    override fun onStop() {
        super.onStop()
        val appActivity = activity as AppActivity
        appActivity.supportActionBar?.title = ""
    }
}
