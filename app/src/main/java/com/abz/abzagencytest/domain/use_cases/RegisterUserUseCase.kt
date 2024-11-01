package com.abz.abzagencytest.domain.use_cases

import com.abz.abzagencytest.domain.UsersRepository
import com.abz.abzagencytest.domain.api.model.register.RegisterComplete
import com.abz.abzagencytest.domain.api.model.token.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UsersRepository) {

    // Registers a user and emits the result as a flow, allowing handling of success or failure.
    suspend fun execute(
        token: String,
        name: String,
        email: String,
        phone: String,
        positionID: Int,
        photo: MultipartBody.Part
    ): Flow<Result<Response<RegisterComplete>>> = flow {
        try {
            emit(Result.success(repository.registerUser(token, name, email, phone, positionID, photo)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    // Requests a new token from the repository, emitting it as a flow.
    suspend fun generateToken(): Flow<Result<Response<Token>>> = flow {
        try {
            emit(Result.success(repository.generateToken()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
