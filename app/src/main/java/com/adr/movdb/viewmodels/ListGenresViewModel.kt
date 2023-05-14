package com.adr.movdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adr.movdb.helper.session.Session
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.Genre
import com.adr.movdb.model.data.GenreListResponse
import com.adr.movdb.model.usecase.GenreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListGenresViewModel @Inject constructor(
    private val genreUseCases: GenreUseCases,
    private val session: Session
    ): ViewModel() {

    private val _listGenre = MutableStateFlow<ApiResponse<GenreListResponse>?>(null)
    val listGenre = _listGenre.asStateFlow()

    fun getListGenre() = viewModelScope.launch {
        genreUseCases.getListGenreUseCase().collect { _listGenre.value = it }
    }

    fun saveListGenre(list: List<Genre>) {
        session.setListGenre(list)
    }
}