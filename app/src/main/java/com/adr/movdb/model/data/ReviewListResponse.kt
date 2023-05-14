package com.adr.movdb.model.data

import com.google.gson.annotations.SerializedName

data class ReviewListResponse(
    val page: Int? = null,
    val results: List<Review> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = null
)
