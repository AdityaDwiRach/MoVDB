package com.adr.movdb.model.repository

import com.adr.movdb.model.api.MovieGenreApi
import com.adr.movdb.model.data.GenreListResponse
import retrofit2.Response
import javax.inject.Inject

class MovieGenreRepositoryImpl @Inject constructor(private val movieGenreApi: MovieGenreApi): MovieGenreRepository {

    override suspend fun getListGenre(): Response<GenreListResponse> = movieGenreApi.getListGenre()
}