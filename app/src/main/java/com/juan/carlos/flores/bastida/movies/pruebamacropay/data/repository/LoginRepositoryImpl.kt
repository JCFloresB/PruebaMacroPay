package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository

import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.auth.AuthDataSource
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
): ILoginRepository {
    override fun login(user: String, password: String): Flow<Result<Boolean>> {
        return dataSource.login(user, password)
    }
}