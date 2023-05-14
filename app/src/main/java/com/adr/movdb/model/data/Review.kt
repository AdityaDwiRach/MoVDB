package com.adr.movdb.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review (
    @SerializedName("author_details")
    val authorDetail: ReviewAuthorDetail? = null,
    val content: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
): Parcelable
