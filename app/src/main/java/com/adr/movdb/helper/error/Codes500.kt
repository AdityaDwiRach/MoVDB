package com.adr.movdb.helper.error

import com.adr.movdb.R

object Codes500 {
    private val invalidService: Triple<Int, String, Int> = Triple(
        501,
        "Invalid service: this service does not exist.",
        R.string.error_admin
    )
    private val serviceOffline: Triple<Int, String, Int> = Triple(
        503,
        "Service offline: This service is temporarily offline, try again later.",
        R.string.error_admin
    )
    val internalError: Triple<Int, String, Int> = Triple(
        500,
        "Internal error: Something went wrong, contact TMDB.",
        R.string.error_admin
    )
    private val failed: Triple<Int, String, Int> = Triple(500, "Failed.", R.string.error_comm)
    private val requestTimeOut: Triple<Int, String, Int> = Triple(
        504,
        "Your request to the backend server timed out. Try again.",
        R.string.error_comm
    )
    private val notConnect: Triple<Int, String, Int> = Triple(
        502,
        "Couldn't connect to the backend server.",
        R.string.error_comm
    )
    private val idInvalid: Triple<Int, String, Int> = Triple(
        500,
        "The ID is invalid.",
        R.string.error_admin
    )
    private val apiOnMaintenance: Triple<Int, String, Int> = Triple(
        503,
        "The API is undergoing maintenance. Try again later.",
        R.string.error_admin
    )

    fun getListCodes500() = listOf(
        invalidService,
        serviceOffline,
        internalError,
        failed,
        requestTimeOut,
        notConnect,
        idInvalid,
        apiOnMaintenance
    )
}
