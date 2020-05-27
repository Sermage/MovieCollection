package com.sermage.mymoviecollection.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BASE_URL_SEARCH="https://api.themoviedb.org/3/search/movie?query=%s";

    private static final String BASE_URL_REVIEWS="https://api.themoviedb.org/3/movie/%s/reviews";

    private static final String BASE_URL_TRAILERS="https://api.themoviedb.org/3/movie/%s/videos";

    private static final String BASE_URL="https://api.themoviedb.org/3/discover/movie";
    private static final String API_KEY="e95df96cf2fe57cc73fe43be0db6c773";
    private static final String PARAMS_KEY="api_key";
    private static final String PARAMS_LANGUAGE="language";
    private static final String PARAMS_SORT_BY="sort_by";
    private static final String PARAMS_PAGE="page";
    private static final String PARAMS_MIN_VOTE_COUNT="vote_count.gte";

    private static final String SORT_BY_POPULARITY="popularity.desc";
    private static final String SORT_BY_VOTE_AVERAGE="vote_average.desc";
    private static final String MIN_VOTE_COUNT_VALUE="1000";

    public static final int POPULARITY=0;
    public static final int TOP_RATED=1;

    public static URL buildURL(int sortBy,int page,String lang){
        URL result=null;
        String methodSort;
        if(sortBy==POPULARITY){
            methodSort=SORT_BY_POPULARITY;
        }else {
            methodSort = SORT_BY_VOTE_AVERAGE;
        }
        Uri uri=Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_KEY,API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE,lang)
                .appendQueryParameter(PARAMS_SORT_BY,methodSort)
                .appendQueryParameter(PARAMS_MIN_VOTE_COUNT,MIN_VOTE_COUNT_VALUE)
                .appendQueryParameter(PARAMS_PAGE,Integer.toString(page)).build();
        try {
            result=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildURLForReviews(int id,String lang){
        URL result=null;

               Uri uri=Uri.parse(String.format(BASE_URL_REVIEWS,id)).buildUpon()
                .appendQueryParameter(PARAMS_KEY,API_KEY).build();

        try {
            result=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildURLForTrailers(int id,String lang){
        URL result=null;

        Uri uri=Uri.parse(String.format(BASE_URL_TRAILERS,id)).buildUpon()
                .appendQueryParameter(PARAMS_KEY,API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE,lang).build();

        try {
            result=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildURLForSearch(String query,String lang){
        URL result=null;

        Uri uri=Uri.parse(String.format(BASE_URL_SEARCH,query)).buildUpon()
                .appendQueryParameter(PARAMS_KEY,API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE,lang).build();

        try {
            result=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

       public static JSONObject getJSONForReviews(int id,String lang){
        JSONObject result=null;
        URL url=buildURLForReviews(id,lang);
        try {
            result=new JSONLoadTask().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
           return result;
    }

    public static JSONObject getJSONForTrailers(int id,String lang){
        JSONObject result=null;
        URL url=buildURLForTrailers(id,lang);
        try {
            result=new JSONLoadTask().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONForSearchableMovies(String query,String lang){
        JSONObject result=null;
        URL url=buildURLForSearch(query,lang);
        try {
            result=new JSONLoadTask().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class JSONLoader extends AsyncTaskLoader<JSONObject>{

        private Bundle bundle;
        private OnStartLoadingListener startLoadingListener;

        public interface OnStartLoadingListener{
            void onStartLoading();
        }

        public void setStartLoadingListener(OnStartLoadingListener startLoadingListener) {
            this.startLoadingListener = startLoadingListener;
        }

        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle=bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if(startLoadingListener!=null) {
                startLoadingListener.onStartLoading();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if(bundle==null){
                return null;
            }
            String urlAsString=bundle.getString("url");
            URL url= null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject result;
            if(url==null){
                return null;
            }
            HttpURLConnection connection=null;
            try {
                connection=(HttpURLConnection)url.openConnection();
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder builder=new StringBuilder();
                String line=reader.readLine();
                while(line!=null){
                    builder.append(line);
                    line=reader.readLine();
                }
                result=new JSONObject(builder.toString());
                return result;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return null;
        }
    }



    private static class JSONLoadTask extends AsyncTask<URL,Void,JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result;
            if (urls == null || urls.length == 0) {
                return null;
            }
            HttpURLConnection connection=null;
            try {
                connection=(HttpURLConnection)urls[0].openConnection();
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder builder=new StringBuilder();
                String line=reader.readLine();
                while(line!=null){
                    builder.append(line);
                    line=reader.readLine();
                }
                result=new JSONObject(builder.toString());
                return result;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return null;
        }
    }

}
