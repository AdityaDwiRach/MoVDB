package com.adr.movdb.model.usecase

import com.adr.movdb.model.usecase.genre.GetListGenreUseCase

data class GenreUseCases(
    val getListGenreUseCase: GetListGenreUseCase
)
