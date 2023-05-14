package com.adr.movdb.model.repository

import com.adr.movdb.model.data.MovieListResponse
import retrofit2.Response

interface MovieDiscoverRepository {

    suspend fun getListMovieByGenre(pageNumber: Int, genreID: String): Response<MovieListResponse>
}