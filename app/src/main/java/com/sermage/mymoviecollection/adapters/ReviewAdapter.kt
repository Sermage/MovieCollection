package com.sermage.mymoviecollection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sermage.mymoviecollection.R
import com.sermage.mymoviecollection.pojo.Reviews
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewAdapter:RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {
    inner class ReviewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewAuthor = itemView.textViewAuthor
        val textViewContent = itemView.textViewContent
        val buttonSeeMore = itemView.buttonSeeMore
    }

    var reviews= listOf<Reviews>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(
            R.layout.review_item,
            parent,
            false
        )
        return ReviewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        with(holder) {
            textViewAuthor.text = reviews[position].author
            textViewContent.text = reviews[position].content
            itemView.buttonSeeMore.setOnClickListener {
                if (buttonSeeMore.text.toString().equals("Read more...", ignoreCase = true)) {
                    textViewContent.maxLines = 1000
                    buttonSeeMore.setText(R.string.read_less)
                } else {
                    textViewContent.maxLines = 4
                    buttonSeeMore.setText(R.string.read_more)
                }
            }
            itemView.textViewContent.setOnClickListener {
                textViewContent.maxLines = 4
                buttonSeeMore.setText(R.string.read_more)
            }
        }

    }

    override fun getItemCount(): Int {
      return reviews.size
    }
}