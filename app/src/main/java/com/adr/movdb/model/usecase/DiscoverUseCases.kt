package com.adr.movdb.model.usecase

import com.adr.movdb.model.usecase.discover.GetListMovieByGenreUseCase

data class DiscoverUseCases(
    val getListMovieByGenreUseCase: GetListMovieByGenreUseCase
)
