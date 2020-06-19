package com.sermage.mymoviecollection.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sermage.mymoviecollection.R;
import com.sermage.mymoviecollection.pojo.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<Movie> movies;
    private OnClickMoviePosterListener posterListener;
    private OnReachEndListener reachEndListener;
    private static final String IMAGE_PATH="https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE="w185";

    public MovieAdapter() {
        movies = new ArrayList<>();
    }

    public interface OnClickMoviePosterListener{
        void onClickMoviePoster(int position);
    }

    public void setPosterListener(OnClickMoviePosterListener posterListener) {
        this.posterListener = posterListener;
    }

    public interface OnReachEndListener{
        void onReachEnd();
    }

    public void setReachEndListener(OnReachEndListener reachEndListener) {
        this.reachEndListener = reachEndListener;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);

        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie=movies.get(position);
        Picasso.get().load(IMAGE_PATH+SMALL_POSTER_SIZE+movie.getPosterPath()).placeholder(R.drawable.no_poster).into(holder.imageViewMoviePoster);
        if(movies.size()>=20 && position==movies.size()-4 && reachEndListener!=null){
            reachEndListener.onReachEnd();
        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

         private ImageView imageViewMoviePoster;

         public MovieHolder(@NonNull View itemView) {
             super(itemView);
             imageViewMoviePoster=itemView.findViewById(R.id.imageViewMoviePoster);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                    if(posterListener!=null){
                        posterListener.onClickMoviePoster(getAdapterPosition());
                    }
                 }
             });
         }
     }
     public void clear(){
        movies.clear();
        notifyDataSetChanged();
     }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
    public void addMovies(List<Movie> movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
}
