package com.abz.abzagencytest.domain.api.model.users


import com.google.gson.annotations.SerializedName


data class Links(
    @SerializedName("next_url")
    val nextUrl: String?,
    @SerializedName("prev_url")
    val prevUrl: Any?
)