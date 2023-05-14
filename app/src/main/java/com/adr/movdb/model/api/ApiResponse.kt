package com.adr.movdb.model.api

sealed class ApiResponse<T>(val data: T? = null, val error: Triple<Int, String, Int>? = null) {

    class Loading<T>(data: T? = null): ApiResponse<T>(data)

    class Empty<T>(data: T? = null): ApiResponse<T>(data)

    class Completed<T>(data: T? = null): ApiResponse<T>(data)

    class Error<T>(data: T? = null, error: Triple<Int, String, Int>): ApiResponse<T>(data, error)
}
