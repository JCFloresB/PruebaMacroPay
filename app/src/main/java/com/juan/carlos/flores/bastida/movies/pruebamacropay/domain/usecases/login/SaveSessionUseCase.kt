package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login

import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.ILoginRepository
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(
    private val repository: ILoginRepository
) {

    suspend operator fun invoke() {
        repository.saveSession(true)
    }


}