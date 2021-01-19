package com.example.jetpackcomposelearning.networking

import com.example.jetpackcomposelearning.data.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("total_pages")
    @Expose
    var totalPages: Long? = 0,
    @SerializedName("total_results")
    @Expose
    var totalResults: Long? = 0,
    @SerializedName("results")
    @Expose
    var results: List<Movie>?
)