package com.adr.movdb.model.repository

import com.adr.movdb.model.data.ConfigurationResponse
import retrofit2.Response

interface ConfigurationRepository {

    suspend fun getConfiguration(): Response<ConfigurationResponse>
}