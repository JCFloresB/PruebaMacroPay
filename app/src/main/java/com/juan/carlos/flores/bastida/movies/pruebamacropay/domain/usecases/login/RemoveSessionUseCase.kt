package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login

import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import javax.inject.Inject

class RemoveSessionUseCase @Inject constructor(
    private val repository: ILoginRepository
) {
    operator suspend fun invoke() = repository.saveSession(false)
}