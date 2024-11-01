package com.abz.abzagencytest.domain.use_cases

import com.abz.abzagencytest.domain.UsersRepository
import com.abz.abzagencytest.domain.api.model.users.UsersModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UsersRepository) {

    // Executes a user data fetch based on the page number, and emits a Result flow
    fun execute(page: Int): Flow<Result<Response<UsersModel>>> = flow {
        try {
            val users = repository.getUsersData(page)
            emit(Result.success(users))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
