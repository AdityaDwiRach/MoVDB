package com.adr.movdb.helper.error

import com.adr.movdb.model.data.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody

object ErrorResponseBuilder {

    fun parseResponseBody(responseBody: ResponseBody): Triple<Int, String, Int> =
        getErrorCopyWriting(Gson().fromJson(responseBody.charStream(), ErrorResponse::class.java))

    private fun getErrorCopyWriting(errorResponse: ErrorResponse): Triple<Int, String, Int> {
        val errorCode = errorResponse.statusCode
        val errorMessage = errorResponse.statusMessage
        return if (errorCode.toString().first() == '4') {
            Codes400.getListCodes400().find { (codes, message, _) -> codes == errorCode && message == errorMessage }
                ?: run {
                    Codes400.inputNotValid
                }
        } else {
            Codes500.getListCodes500().find { (codes, message, _) -> codes == errorCode && message == errorMessage }
                ?: run {
                    Codes500.internalError
                }
        }
    }
}
