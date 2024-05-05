package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.movies.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail.GetMovieDetailResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.movie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetailUseCase: GetMovieUseCase
): ViewModel() {

    val movieDetail: StateFlow<Result<GetMovieDetailResponse>> =
        movieDetailUseCase(savedStateHandle.get<Int>("movieId")!!).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Result.Loading()
        )
}
