package com.adr.movdb.model.repository

import com.adr.movdb.model.data.MovieDetail
import com.adr.movdb.model.data.ReviewListResponse
import com.adr.movdb.model.data.VideoListResponse
import retrofit2.Response

interface MovieDetailRepository {

    suspend fun getDetailMovie(movieID: String): Response<MovieDetail>

    suspend fun getListReviewMovie(movieID: String, pageNumber: Int): Response<ReviewListResponse>

    suspend fun getListVideoMovie(movieID: String): Response<VideoListResponse>
}