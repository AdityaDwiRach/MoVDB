package com.adr.movdb.model.api

import com.adr.movdb.model.data.MovieDetail
import com.adr.movdb.model.data.ReviewListResponse
import com.adr.movdb.model.data.VideoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailApi {

    @GET(Api.GET_DETAIL_MOVIE)
    suspend fun getDetailMovie(
        @Path("id") movieID: String,
        @Query("language") language: String = "en-US"
    ): Response<MovieDetail>

    @GET(Api.GET_DETAIL_MOVIE_LIST_REVIEW)
    suspend fun getListReviewMovie(
        @Path("id") movieID: String,
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int
    ): Response<ReviewListResponse>

    @GET(Api.GET_DETAIL_MOVIE_LIST_VIDEO)
    suspend fun getListVideoMovie(
        @Path("id") movieID: String,
        @Query("language") language: String = "en-US"
    ): Response<VideoListResponse>
}