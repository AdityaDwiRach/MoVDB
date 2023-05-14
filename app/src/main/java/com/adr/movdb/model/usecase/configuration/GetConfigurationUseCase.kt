package com.adr.movdb.model.usecase.configuration

import com.adr.movdb.helper.error.Codes500
import com.adr.movdb.helper.error.ErrorResponseBuilder
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.ConfigurationResponse
import com.adr.movdb.model.repository.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(private val configurationRepository: ConfigurationRepository) {

    operator fun invoke(): Flow<ApiResponse<ConfigurationResponse>> = flow {
        emit(ApiResponse.Loading())
        try {
            val response = configurationRepository.getConfiguration()
            if (response.isSuccessful) {
                emit( response.body()?.let {
                    ApiResponse.Completed(response.body())
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