package com.abz.abzagencytest.domain.api.model.users


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("position")
    val position: String?,
    @SerializedName("position_id")
    val positionId: String?,
    @SerializedName("registration_timestamp")
    val registrationTimestamp: Int?
)