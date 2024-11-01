package com.abz.abzagencytest.domain.api.model.register


import com.google.gson.annotations.SerializedName

data class RegisterComplete(
    @SerializedName("fails")
    val fails: Fails?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("user_id")
    val userID: Int?,
    @SerializedName("success")
    val success: Boolean?
)