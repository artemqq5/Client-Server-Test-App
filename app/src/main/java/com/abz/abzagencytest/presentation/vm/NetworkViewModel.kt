package com.abz.abzagencytest.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abz.abzagencytest.domain.use_cases.NetworkStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkStatusUseCase: NetworkStatusUseCase
) : ViewModel() {

    private val _isNetworkConnected = MutableStateFlow<Boolean>(networkStatusUseCase.isConnected())
    val isNetworkConnected: StateFlow<Boolean> = _isNetworkConnected

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkStatusUseCase.observeNetworkStatus().collect { isConnected ->
                _isNetworkConnected.value = isConnected
            }
        }
    }
}
