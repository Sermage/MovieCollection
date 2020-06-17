package com.sermage.mymoviecollection.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sermage.mymoviecollection.R;
import com.sermage.mymoviecollection.pojo.Reviews;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Reviews> reviews;

    public ReviewAdapter() {
        reviews=new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.textViewAuthor.setText(reviews.get(position).getAuthor());
        holder.textViewContent.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    class ReviewHolder extends RecyclerView.ViewHolder{
        TextView textViewAuthor;
        TextView textViewContent;
        Button buttonSeeMore;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor=itemView.findViewById(R.id.textViewAuthor);
            textViewContent=itemView.findViewById(R.id.textViewContent);
            buttonSeeMore=itemView.findViewById(R.id.buttonSeeMore);
            buttonSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(buttonSeeMore.getText().toString().equalsIgnoreCase("Read more...")){
                        textViewContent.setMaxLines(1000);
                        buttonSeeMore.setText(R.string.read_less);
                    }else{
                        textViewContent.setMaxLines(4);
                        buttonSeeMore.setText(R.string.read_more);
                    }

                }
            });
        }
    }
    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

}
