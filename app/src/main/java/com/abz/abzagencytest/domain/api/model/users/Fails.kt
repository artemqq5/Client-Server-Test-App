package com.abz.abzagencytest.domain.api.model.users


import com.google.gson.annotations.SerializedName

data class Fails(
    @SerializedName("count")
    val count: List<String?>?,
    @SerializedName("page")
    val page: List<String?>?
)