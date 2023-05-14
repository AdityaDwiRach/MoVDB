package com.adr.movdb.model.repository

import com.adr.movdb.model.api.MovieDiscoverApi
import com.adr.movdb.model.data.MovieListResponse
import retrofit2.Response
import javax.inject.Inject

class MovieDiscoverRepositoryImpl @Inject constructor(private val movieDiscoverApi: MovieDiscoverApi): MovieDiscoverRepository {

    override suspend fun getListMovieByGenre(pageNumber: Int, genreID: String): Response<MovieListResponse> =
        movieDiscoverApi.getListMovieByGenre(pageNumber = pageNumber, genreID = genreID)
}