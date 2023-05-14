package com.adr.movdb.model.data

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val genres: List<Genre> = listOf(),
    val homepage: String? = null,
    val id: Int? = null,
    val overview: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
)
