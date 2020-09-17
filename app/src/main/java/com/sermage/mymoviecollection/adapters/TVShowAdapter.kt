package com.sermage.mymoviecollection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.pojo.Movie
import com.sermage.mymoviecollection.pojo.TVShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class TVShowAdapter:RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {
    inner class TVShowViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageViewMoviePoster=itemView.imageViewMoviePoster
        val textViewRating=itemView.textViewRating
    }

    var tvShows= mutableListOf<TVShow>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }
    var posterListener: OnClickTVShowPosterListener? = null
    var reachEndListener: OnReachEndListener? = null

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/"
    private val SMALL_POSTER_SIZE = "w185"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val movie = tvShows[position]
        Picasso.get().load(IMAGE_PATH + SMALL_POSTER_SIZE + movie.posterPath)
            .into(holder.imageViewMoviePoster)
        holder.textViewRating.text=movie.voteAverage.toString()
        if (tvShows.size >= 20 && position == tvShows.size - 4 && reachEndListener != null) {
            reachEndListener?.onReachEnd()
        }
        holder.itemView.setOnClickListener {
            posterListener?.onClickTVShowPoster(position)
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
    interface OnClickTVShowPosterListener {
        fun onClickTVShowPoster(position: Int)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }
    fun clear(){
        tvShows.clear()
        notifyDataSetChanged()
    }

}