package com.adr.movdb.model.usecase

import com.adr.movdb.model.usecase.detail.GetDetailMovieUseCase
import com.adr.movdb.model.usecase.detail.GetListReviewMovieUseCase
import com.adr.movdb.model.usecase.detail.GetListVideoMovieUseCase

data class DetailUseCases(
    val getDetailMovieUseCase: GetDetailMovieUseCase,
    val getListReviewMovieUseCase: GetListReviewMovieUseCase,
    val getListVideoMovieUseCase: GetListVideoMovieUseCase
)
