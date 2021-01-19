package com.example.jetpackcomposelearning.listscreen

import com.example.jetpackcomposelearning.data.Movie
import com.example.jetpackcomposelearning.networking.Api
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val movieApiService: Api
) {

    suspend fun getMovies(): List<Movie>? {
        return try {
            movieApiService.fetchMovies().body()!!.results
        } catch (exception: Exception){
            null
        }
    }
}