package com.adr.movdb.model.api

import com.adr.movdb.model.data.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDiscoverApi {

    @GET(Api.GET_LIST_MOVIE)
    suspend fun getListMovieByGenre(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") pageNumber: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") genreID: String
    ): Response<MovieListResponse>
}