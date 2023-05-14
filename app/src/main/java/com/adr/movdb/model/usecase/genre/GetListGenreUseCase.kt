package com.adr.movdb.model.usecase.genre

import com.adr.movdb.helper.error.Codes500
import com.adr.movdb.helper.error.ErrorResponseBuilder
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.GenreListResponse
import com.adr.movdb.model.repository.MovieGenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetListGenreUseCase @Inject constructor(private val movieGenreRepository: MovieGenreRepository) {

    operator fun invoke(): Flow<ApiResponse<GenreListResponse>> = flow {
        emit(ApiResponse.Loading())
        try {
            val response = movieGenreRepository.getListGenre()
            if (response.isSuccessful) {
                emit( response.body()?.let {
                    if (it.genres.isNotEmpty()) ApiResponse.Completed(response.body())
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