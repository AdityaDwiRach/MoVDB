package com.adr.movdb.helper.error

import com.adr.movdb.R

object Codes400 {
    private val authFailedPermission: Triple<Int, String, Int> = Triple(
        401,
        "Authentication failed: You do not have permissions to access the service.",
        R.string.error_auth_admin
    )
    private val invalidFormat: Triple<Int, String, Int> = Triple(
        405,
        "Invalid format: This service doesn't exist in that format.",
        R.string.error_input
    )
    private val invalidParam: Triple<Int, String, Int> = Triple(
        422,
        "Invalid parameters: Your request parameters are incorrect.",
        R.string.error_input
    )
    private val invalidID: Triple<Int, String, Int> = Triple(
        404,
        "Invalid id: The pre-requisite id is invalid or not found.",
        R.string.error_input
    )
    private val invalidAPIKey: Triple<Int, String, Int> = Triple(
        401,
        "Invalid API key: You must be granted a valid key.",
        R.string.error_admin
    )
    private val duplicateEntry: Triple<Int, String, Int> = Triple(
        403,
        "Duplicate entry: The data you tried to submit already exists.",
        R.string.error_entry_duplicated
    )
    private val suspendedAPIKey: Triple<Int, String, Int> = Triple(
        401,
        "Suspended API key: Access to your account has been suspended, contact TMDB.",
        R.string.error_admin
    )
    private val authFailed: Triple<Int, String, Int> = Triple(
        401,
        "Authentication failed.",
        R.string.error_auth_admin
    )
    private val deviceDenied: Triple<Int, String, Int> = Triple(
        401,
        "Device denied.",
        R.string.error_admin
    )
    private val sessionDenied: Triple<Int, String, Int> = Triple(
        401,
        "Session denied.",
        R.string.error_comm
    )
    private val validationFailed: Triple<Int, String, Int> = Triple(
        400,
        "Validation failed.",
        R.string.error_comm
    )
    private val invalidHeader: Triple<Int, String, Int> = Triple(
        406,
        "Invalid accept header.",
        R.string.error_admin
    )
    private val invalidDateRange: Triple<Int, String, Int> = Triple(
        422,
        "Invalid date range: Should be a range no longer than 14 days.",
        R.string.error_input
    )
    private val invalidPage: Triple<Int, String, Int> = Triple(
        400,
        "Invalid page: Pages start at 1 and max at 1000. They are expected to be an integer.",
        R.string.error_comm
    )
    private val invalidDate: Triple<Int, String, Int> = Triple(
        400,
        "Invalid date: Format needs to be YYYY-MM-DD.",
        R.string.error_input
    )
    private val requestOverLimit: Triple<Int, String, Int> = Triple(
        429,
        "Your request count (#) is over the allowed limit of (40).",
        R.string.error_admin
    )
    private val mustProvideCreds: Triple<Int, String, Int> = Triple(
        400,
        "You must provide a username and password.",
        R.string.error_input
    )
    private val tooManyAppend: Triple<Int, String, Int> = Triple(
        400,
        "Too many append to response objects: The maximum number of remote calls is 20.",
        R.string.error_comm
    )
    private val invalidTimezone: Triple<Int, String, Int> = Triple(
        400,
        "Invalid timezone: Please consult the documentation for a valid timezone.",
        R.string.error_input
    )
    private val mustConfirmAction: Triple<Int, String, Int> = Triple(
        400,
        "You must confirm this action: Please provide a confirm=true parameter.",
        R.string.error_input
    )
    private val invalidCreds: Triple<Int, String, Int> = Triple(
        401,
        "Invalid username and/or password: You did not provide a valid login.",
        R.string.error_input
    )
    private val accountDisabled: Triple<Int, String, Int> = Triple(
        401,
        "Account disabled: Your account is no longer active. Contact TMDB if this is an error.",
        R.string.error_admin
    )
    private val emailNotVerified: Triple<Int, String, Int> = Triple(
        401,
        "Email not verified: Your email address has not been verified.",
        R.string.error_admin
    )
    private val invalidExpiredToken: Triple<Int, String, Int> = Triple(
        401,
        "Invalid request token: The request token is either expired or invalid.",
        R.string.error_admin
    )
    private val resourceNotFound: Triple<Int, String, Int> = Triple(
        404,
        "The resource you requested could not be found.",
        R.string.error_entry_not_found
    )
    private val invalidToken: Triple<Int, String, Int> = Triple(
        401,
        "Invalid token.",
        R.string.error_admin
    )
    private val tokenNotGranted: Triple<Int, String, Int> = Triple(
        401,
        "This token hasn't been granted write permission by the user.",
        R.string.error_admin
    )
    private val requestSessionNotFound: Triple<Int, String, Int> = Triple(
        404,
        "The requested session could not be found.",
        R.string.error_comm
    )
    private val editPermissionNotGranted: Triple<Int, String, Int> = Triple(
        401,
        "You don't have permission to edit this resource.",
        R.string.error_admin
    )
    private val resourcePrivate: Triple<Int, String, Int> = Triple(
        401,
        "This resource is private.",
        R.string.error_admin
    )
    private val tokenNotApproved: Triple<Int, String, Int> = Triple(
        422,
        "This request token hasn't been approved by the user.",
        R.string.error_admin
    )
    private val methodNotSupported: Triple<Int, String, Int> = Triple(
        405,
        "This request method is not supported for this resource.",
        R.string.error_admin
    )
    private val userSuspended: Triple<Int, String, Int> = Triple(
        403,
        "This user has been suspended.",
        R.string.error_admin
    )
    val inputNotValid: Triple<Int, String, Int> = Triple(
        400,
        "The input is not valid.",
        R.string.error_input
    )

    fun getListCodes400() = listOf(
        authFailedPermission,
        invalidFormat,
        invalidParam,
        invalidID,
        invalidAPIKey,
        duplicateEntry,
        suspendedAPIKey,
        authFailed,
        deviceDenied,
        sessionDenied,
        validationFailed,
        invalidHeader,
        invalidDateRange,
        invalidPage,
        invalidDate,
        requestOverLimit,
        mustProvideCreds,
        tooManyAppend,
        invalidTimezone,
        mustConfirmAction,
        invalidCreds,
        accountDisabled,
        emailNotVerified,
        invalidExpiredToken,
        resourceNotFound,
        invalidToken,
        tokenNotGranted,
        requestSessionNotFound,
        editPermissionNotGranted,
        resourcePrivate,
        tokenNotApproved,
        methodNotSupported,
        userSuspended,
        inputNotValid
    )
}
