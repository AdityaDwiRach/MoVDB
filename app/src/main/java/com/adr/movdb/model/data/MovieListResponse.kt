package com.adr.movdb.model.data

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val page: Int? = null,
    val results: List<Movie> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = null
)
