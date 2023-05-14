package com.adr.movdb.model.api

import com.adr.movdb.model.data.ConfigurationResponse
import retrofit2.Response
import retrofit2.http.GET

interface ConfigurationApi {

    @GET(Api.GET_CONFIGURATION)
    suspend fun getConfiguration(): Response<ConfigurationResponse>
}