package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login.GetSessionUseCase
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.login.RemoveSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val removeSessionUseCase: RemoveSessionUseCase,
    private val getSessionUseCase: GetSessionUseCase
): ViewModel() {

    private val _isLogged = MutableStateFlow<Boolean>(false)
    val isLogged: StateFlow<Boolean> = _isLogged


    fun extracted() {
        viewModelScope.launch {
            getSessionUseCase().catch {
                _isLogged.value = false
            }.collect {
                _isLogged.value = it
            }
        }
    }

}