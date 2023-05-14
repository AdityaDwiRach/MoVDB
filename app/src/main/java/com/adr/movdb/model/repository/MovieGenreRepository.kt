package com.adr.movdb.model.repository

import com.adr.movdb.model.data.GenreListResponse
import retrofit2.Response

interface MovieGenreRepository {

    suspend fun getListGenre(): Response<GenreListResponse>
}