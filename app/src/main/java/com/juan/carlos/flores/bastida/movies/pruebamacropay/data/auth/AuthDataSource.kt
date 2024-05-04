package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.auth

import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthDataSource @Inject constructor() {
    fun login(user: String, password: String): Flow<Result<Boolean>> = flow {
        emit(Result.Loading())
        delay(3000)
        if (user == "jc@gmail.com" && password == "password") {
            emit(Result.Success(true))
        } else {
            emit(Result.Error("User or password incorrect!"))
        }
    }
}