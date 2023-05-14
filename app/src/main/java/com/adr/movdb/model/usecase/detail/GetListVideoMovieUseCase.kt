package com.adr.movdb.model.usecase.detail

import com.adr.movdb.helper.error.Codes500
import com.adr.movdb.helper.error.ErrorResponseBuilder
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.VideoListResponse
import com.adr.movdb.model.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetListVideoMovieUseCase @Inject constructor(private val movieDetailRepository: MovieDetailRepository) {

    operator fun invoke(movieID: String): Flow<ApiResponse<VideoListResponse>> = flow {
        emit(ApiResponse.Loading())
        try {
            val response = movieDetailRepository.getListVideoMovie(movieID)
            if (response.isSuccessful) {
                emit( response.body()?.let {
                    if (it.results.isNotEmpty()) ApiResponse.Completed(response.body())
                    else ApiResponse.Empty()
                } ?: run {
                    ApiResponse.Error(error = Codes500.internalError)
                })
            } else {
                emit(ApiResponse.Error(error = response.errorBody()?.let {
                    ErrorResponseBuilder.parseResponseBody(it)
                } ?: run {
                    Codes500.internalError
                }))
            }
        } catch (e: IOException) {
            emit(ApiResponse.Error(error = Codes500.internalError))
        }
    }
}