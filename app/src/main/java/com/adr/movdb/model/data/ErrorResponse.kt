package com.adr.movdb.model.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    val success: Boolean = false,
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null
)
