package com.abz.abzagencytest.domain

import com.abz.abzagencytest.domain.api.model.register.RegisterComplete
import com.abz.abzagencytest.domain.api.model.token.Token
import com.abz.abzagencytest.domain.api.model.users.UsersModel
import okhttp3.MultipartBody
import retrofit2.Response

interface UsersRepository {
    suspend fun getUsersData(page: Int): Response<UsersModel>
    suspend fun generateToken(): Response<Token>
    suspend fun registerUser(
        token: String,
        name: String,
        email: String,
        phone: String,
        positionID: Int,
        photo: MultipartBody.Part
    ): Response<RegisterComplete>

}
