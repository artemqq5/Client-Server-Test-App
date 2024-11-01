package com.abz.abzagencytest.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abz.abzagencytest.domain.api.model.register.RegisterComplete
import com.abz.abzagencytest.domain.api.model.token.Token
import com.abz.abzagencytest.domain.use_cases.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerResult = MutableSharedFlow<Result<Response<RegisterComplete>>>(replay = 1)
    val registerResult: SharedFlow<Result<Response<RegisterComplete>>> = _registerResult

    private val _generateTokenResult = MutableSharedFlow<Result<Response<Token>>>(replay = 1)
    val generateTokenResult: SharedFlow<Result<Response<Token>>> = _generateTokenResult

    fun registerUser(
        token: String,
        name: String,
        email: String,
        phone: String,
        positionID: Int,
        photo: MultipartBody.Part
    ) {
        viewModelScope.launch {
            registerUserUseCase.execute(token, name, email, phone, positionID, photo)
                .collect { result ->
                    _registerResult.emit(result)
                }
        }
    }

    fun generateToken() {
        viewModelScope.launch {
            registerUserUseCase.generateToken().collect {
                _generateTokenResult.emit(it)
            }
        }
    }
}
