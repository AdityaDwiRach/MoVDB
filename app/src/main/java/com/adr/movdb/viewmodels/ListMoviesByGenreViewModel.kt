package com.adr.movdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adr.movdb.helper.session.Session
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.ConfigurationImage
import com.adr.movdb.model.data.Movie
import com.adr.movdb.model.data.MovieListResponse
import com.adr.movdb.model.usecase.DiscoverUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMoviesByGenreViewModel @Inject constructor(
    private val discoverUseCases: DiscoverUseCases,
    val session: Session
): ViewModel() {

    private val _listMovie = MutableStateFlow<ApiResponse<MovieListResponse>?>(null)
    val listMovie = _listMovie.asStateFlow()

    private val _totalListMovie = MutableStateFlow<List<Movie>>(listOf())
    val totalListMovie = _totalListMovie.asStateFlow()

    var pageNow = 0
    var maxPage = 0
    var genreID = "0"

    fun getListMovieByGenre() = viewModelScope.launch {
        if (isEligibleToRefresh() || (pageNow == 0 && maxPage == 0)) {
            discoverUseCases.getListMovieByGenreUseCase(pageNumber = (pageNow + 1), genreID = genreID)
                .collect { _listMovie.value = it }
        }
    }

    fun setTotalListMovie(data: List<Movie>) {
        _totalListMovie.value += data
    }

    private fun isEligibleToRefresh() = pageNow < maxPage

    fun getConfigurationImage(): ConfigurationImage = session.getConfigImage()
}