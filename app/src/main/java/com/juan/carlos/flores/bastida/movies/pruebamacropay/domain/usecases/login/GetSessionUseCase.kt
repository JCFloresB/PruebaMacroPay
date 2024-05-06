package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login

import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val repository: ILoginRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getSession()
}