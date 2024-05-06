package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository

import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    fun login(user: String, password:String): Flow<Result<Boolean>>

    suspend fun saveSession(isLogged: Boolean)

    fun getSession(): Flow<Boolean>
}