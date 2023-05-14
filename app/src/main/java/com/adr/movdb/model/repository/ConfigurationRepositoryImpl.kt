package com.adr.movdb.model.repository

import com.adr.movdb.model.api.ConfigurationApi
import com.adr.movdb.model.data.ConfigurationResponse
import retrofit2.Response
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(private val configurationApi: ConfigurationApi): ConfigurationRepository {

    override suspend fun getConfiguration(): Response<ConfigurationResponse> {
        return configurationApi.getConfiguration()
    }
}