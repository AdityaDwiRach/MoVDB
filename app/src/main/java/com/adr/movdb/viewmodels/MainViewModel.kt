package com.adr.movdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adr.movdb.helper.session.Session
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.ConfigurationImage
import com.adr.movdb.model.data.ConfigurationResponse
import com.adr.movdb.model.usecase.ConfigurationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val configurationUseCases: ConfigurationUseCases,
    private val session: Session
    ): ViewModel() {

    private val _configurationResponse = MutableStateFlow<ApiResponse<ConfigurationResponse>?>(null)
    val configurationResponse = _configurationResponse.asStateFlow()

    fun getConfiguration() = viewModelScope.launch {
        configurationUseCases.getConfigurationUseCase().collect { _configurationResponse.value = it }
    }

    fun saveConfiguration(configResponse: ConfigurationResponse) {
        with(configResponse.images) {
            baseURL?.let {
                session.setConfigImage(ConfigurationImage(it, backdropSizes.first(), posterSizes.first()))
            }
        }
    }
}