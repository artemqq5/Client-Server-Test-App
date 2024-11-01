package com.abz.abzagencytest.data

import com.abz.abzagencytest.domain.UsersRepository
import com.abz.abzagencytest.domain.api.ABZAgencyAPI
import com.abz.abzagencytest.domain.api.model.register.RegisterComplete
import com.abz.abzagencytest.domain.api.model.token.Token
import com.abz.abzagencytest.domain.api.model.users.UsersModel
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val abzAgencyAPI: ABZAgencyAPI) :
    UsersRepository {

    // Fetches a paginated list of users. The `page` parameter specifies the page of results to retrieve.
    override suspend fun getUsersData(page: Int): Response<UsersModel> {
        return abzAgencyAPI.getUsers(page)
    }

    // Requests a new token from the server. Tokens are typically used for authenticating further requests.
    override suspend fun generateToken(): Response<Token> {
        return abzAgencyAPI.generateToken()
    }

    // Registers a new user, with the `token` parameter used for request authentication.
    // The `photo` parameter utilizes MultipartBody.Part for uploading the user's photo as a file.
    override suspend fun registerUser(
        token: String,
        name: String,
        email: String,
        phone: String,
        positionID: Int,
        photo: MultipartBody.Part
    ): Response<RegisterComplete> {
        return abzAgencyAPI.registerUser(token, name, email, phone, positionID, photo)
    }
}
