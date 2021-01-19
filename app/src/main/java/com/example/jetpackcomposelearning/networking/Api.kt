package com.example.jetpackcomposelearning.networking

import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("movie/popular?language=en-US&region=US&page=1&api_key=e3bb3f2ea8f7b92c76b4078afe5453ba")
    suspend fun fetchMovies(): Response<MovieResponse?>
}