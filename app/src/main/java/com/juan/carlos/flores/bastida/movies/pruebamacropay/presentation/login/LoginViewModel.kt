package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login.LoginUseCase
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login.SaveSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveSessionUseCase: SaveSessionUseCase
): ViewModel() {

    private val _isLoginSuccess = MutableStateFlow<Result<Boolean>?>(null)
    val isLoginSuccess: StateFlow<Result<Boolean>?> = _isLoginSuccess

    fun loginHandled(user: String, password: String) {
        viewModelScope.launch {
            loginUseCase(user, password)
                .collect { r ->
                    _isLoginSuccess.value = r
                    if (r is Result.Success) {
                        saveSessionUseCase()
                    }
                }
        }
    }

}