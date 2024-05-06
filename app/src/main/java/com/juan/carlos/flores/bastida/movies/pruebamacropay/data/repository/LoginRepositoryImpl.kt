package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository

import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.auth.AuthDataSource
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.local.UserPreferences
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource,
    private val preferences: UserPreferences
): ILoginRepository {
    override fun login(user: String, password: String): Flow<Result<Boolean>> {
        return dataSource.login(user, password)
    }

    override suspend fun saveSession(isLogged: Boolean) {
        preferences.setIsLoggedIn(isLogged)
    }

    override fun getSession(): Flow<Boolean> {
        return preferences.isLoggedIn
    }
}