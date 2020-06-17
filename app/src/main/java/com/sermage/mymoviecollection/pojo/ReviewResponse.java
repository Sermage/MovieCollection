package com.sermage.mymoviecollection.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {


        @SerializedName("results")
        @Expose
        private List<Reviews> reviews = null;

        public List<Reviews> getResults() {
            return reviews;
        }

        public void setResults(List<Reviews> results) {
            this.reviews = reviews;
        }
}
