package com.adr.movdb.model.api

object Api {
    const val GET_CONFIGURATION = "configuration"
    const val GET_LIST_GENRE = "genre/movie/list"
    const val GET_LIST_MOVIE = "discover/movie"
    const val GET_DETAIL_MOVIE = "movie/{id}"
    const val GET_DETAIL_MOVIE_LIST_REVIEW = "${GET_DETAIL_MOVIE}/reviews"
    const val GET_DETAIL_MOVIE_LIST_VIDEO = "${GET_DETAIL_MOVIE}/videos "
}