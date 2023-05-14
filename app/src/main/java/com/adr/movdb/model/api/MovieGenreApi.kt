package com.adr.movdb.model.api

import com.adr.movdb.model.data.GenreListResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieGenreApi {

    @GET(Api.GET_LIST_GENRE)
    suspend fun getListGenre(): Response<GenreListResponse>
}