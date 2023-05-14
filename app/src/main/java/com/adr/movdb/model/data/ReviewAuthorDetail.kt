package com.adr.movdb.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewAuthorDetail(
    val username: String? = null,
    @SerializedName("avatar_path")
    val avatarPath: String? = null,
    val rating: Int? = null
): Parcelable
