package com.example.jetpackcomposelearning.data

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("id")
    val id: Long,

    @SerializedName(value = "header", alternate = ["title", "name"])
    val header: String,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName(value = "description", alternate = ["overview", "synopsis"])
    var description: String?,

    @SerializedName("release_date")
    var releaseDate: String?,

    @SerializedName("vote_count")
    var voteCount: Int,

    var status: String?
)