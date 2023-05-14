package com.adr.movdb.model.data

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("base_url")
    val baseURL: String? = null,
    @SerializedName("backdrop_sizes")
    val backdropSizes: List<String> = listOf(),
    @SerializedName("poster_sizes")
    val posterSizes: List<String> = listOf()
)
