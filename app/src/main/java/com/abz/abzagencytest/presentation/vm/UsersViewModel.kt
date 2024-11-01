package com.abz.abzagencytest.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abz.abzagencytest.domain.api.model.users.UsersModel
import com.abz.abzagencytest.domain.use_cases.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) :
    ViewModel() {

    private val _users = MutableStateFlow<Result<Response<UsersModel>?>>(Result.success(null))
    val users: StateFlow<Result<Response<UsersModel>?>> = _users

    init {
        fetchUsers()
    }

    fun fetchUsers(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUseCase.execute(page).collect { result ->
                _users.value = result
            }
        }
    }

}
