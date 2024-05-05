package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login

import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository.LoginRepositoryImpl
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
) {
    operator fun invoke(user: String, password: String): Flow<Result<Boolean>> {
        return loginRepositoryImpl.login(user, password)
    }
}