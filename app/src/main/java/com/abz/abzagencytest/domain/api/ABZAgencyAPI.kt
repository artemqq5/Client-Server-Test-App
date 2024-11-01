package com.abz.abzagencytest.domain.api

import com.abz.abzagencytest.domain.api.model.register.RegisterComplete
import com.abz.abzagencytest.domain.api.model.token.Token
import com.abz.abzagencytest.domain.api.model.users.UsersModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ABZAgencyAPI {

    companion object {
        // Base URL for the API endpoints, used to construct complete request URLs.
        const val BASE_URL = "https://frontend-test-assignment-api.abz.agency/api/v1/"
    }

    @GET("token")
    // Retrieves a new `Token` for authenticating requests. This call does not require additional parameters.
    suspend fun generateToken(): Response<Token>

    @GET("users")
    // Fetches a list of users. The `page` parameter allows pagination, and `count` specifies the number of users per page.
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("count") count: Int = 6
    ): Response<UsersModel>

    @Multipart
    @POST("users")
    // Registers a new user. The `token` parameter is used for authorization in the header,
    // while user data is passed as multipart form-data parts.
    suspend fun registerUser(
        @Header("Token") token: String, // Token for authorization
        @Part("name") name: String, // User's name
        @Part("email") email: String, // User's email
        @Part("phone") phone: String, // User's phone number
        @Part("position_id") positionID: Int, // Position ID to associate with the user
        @Part photo: MultipartBody.Part // User's photo uploaded as a file part
    ): Response<RegisterComplete>
}
