package com.abz.abzagencytest.domain.api.model.users


import com.google.gson.annotations.SerializedName

data class UsersModel(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("fails")
    val fails: Fails?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_users")
    val totalUsers: Int?,
    @SerializedName("users")
    val users: List<User>?
)