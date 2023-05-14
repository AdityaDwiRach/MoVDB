package com.adr.movdb.model.repository

import com.adr.movdb.model.api.MovieDetailApi
import com.adr.movdb.model.data.MovieDetail
import com.adr.movdb.model.data.ReviewListResponse
import com.adr.movdb.model.data.VideoListResponse
import retrofit2.Response
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(private val movieDetailApi: MovieDetailApi): MovieDetailRepository {

    override suspend fun getDetailMovie(movieID: String): Response<MovieDetail> = movieDetailApi.getDetailMovie(movieID)

    override suspend fun getListReviewMovie(movieID: String, pageNumber: Int): Response<ReviewListResponse> =
        movieDetailApi.getListReviewMovie(movieID, pageNumber = pageNumber)

    override suspend fun getListVideoMovie(movieID: String): Response<VideoListResponse> =
        movieDetailApi.getListVideoMovie(movieID)

}