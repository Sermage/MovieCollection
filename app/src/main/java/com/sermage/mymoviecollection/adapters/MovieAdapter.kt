package com.sermage.mymoviecollection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.pojo.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter:RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    inner class MovieHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageViewMoviePoster=itemView.imageViewMoviePoster
        val textViewRating=itemView.textViewRating

    }

    var movies= mutableListOf<Movie>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    var posterListener: OnClickMoviePosterListener? = null
    var reachEndListener: OnReachEndListener? = null

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/"
    private val SMALL_POSTER_SIZE = "w185"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movies[position]
        Picasso.get().load(IMAGE_PATH + SMALL_POSTER_SIZE + movie.posterPath)
            .into(holder.imageViewMoviePoster)
        holder.textViewRating.text=movie.voteAverage.toString()
        if (movies.size >= 20 && position == movies.size - 4 && reachEndListener != null) {
            reachEndListener?.onReachEnd()
        }
        holder.itemView.setOnClickListener {
            posterListener?.onClickMoviePoster(position)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    interface OnClickMoviePosterListener {
        fun onClickMoviePoster(position: Int)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }
    fun clear(){
        movies.clear()
        notifyDataSetChanged()
    }

}