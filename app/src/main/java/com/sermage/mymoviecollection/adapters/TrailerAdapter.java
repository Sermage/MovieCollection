package com.sermage.mymoviecollection.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sermage.mymoviecollection.R;
import com.sermage.mymoviecollection.pojo.Trailers;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private List<Trailers> trailers;
    private OnTrailerClickListener trailerClickListener;

    public TrailerAdapter() {
        trailers=new ArrayList<>();
    }

    public interface OnTrailerClickListener{
        void onTrailerClick(String url);
    }

    public void setTrailerClickListener(OnTrailerClickListener trailerClickListener) {
        this.trailerClickListener = trailerClickListener;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        holder.textViewNameOfTrailer.setText(trailers.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder{

        TextView textViewNameOfTrailer;
        private static final String YOUTUBE_URL="https://www.youtube.com/watch?v=";

        public TrailerHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameOfTrailer=itemView.findViewById(R.id.textViewNameOfTrailer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trailerClickListener.onTrailerClick(YOUTUBE_URL+trailers.get(getAdapterPosition()).getKey());
                }
            });
        }
    }

    public void setTrailers(List<Trailers> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }
}
