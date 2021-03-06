package com.sermage.mymoviecollection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.pojo.Trailers
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.trailer_item.view.*


class TrailerAdapter : RecyclerView.Adapter<TrailerAdapter.TrailerHolder>() {
    inner class TrailerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNameOfTrailer = itemView.textViewNameOfTrailer
        val imageViewPlay = itemView.imageViewPlay
    }

    private val YOUTUBE_URL = "https://www.youtube.com/watch?v="
    var onTrailerClickListener: OnTrailerClickListener? = null

    var trailers = listOf<Trailers>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        return TrailerHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerHolder, position: Int) {
        holder.textViewNameOfTrailer.text = trailers[position].name
        val path = YOUTUBE_URL + trailers[position].key
        Picasso.get().load("https://img.youtube.com/vi/${trailers[position].key}/1.jpg")
            .into(holder.imageViewPlay)
        holder.itemView.setOnClickListener {
            onTrailerClickListener?.onTrailerClick(path)
        }
    }


    override fun getItemCount(): Int {
        return trailers.size
    }

    interface OnTrailerClickListener {
        fun onTrailerClick(url: String?)
    }

}

