package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.movies.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import timber.log.Timber

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {
    /*private val _movies = MutableStateFlow<Result<List<Movie>>?>(null)
    val movies: StateFlow<Result<List<Movie>>?> = _movies
    private var pageNumber: Int = 1
    private var lastPage = 0*/
    /*init {
        getMovies()
    }*/

    val moviePagingDataFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase().cachedIn(viewModelScope)
    /*fun getMovies() {
        Timber.d("pageNumber: $pageNumber")
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase(pageNumber)
                .catch {
                    Timber.e("Error: $it")
                }
                .collect {
                    it.data?.let {
                        _movies.value = Result.Success(it.results)
                        pageNumber = it.page + 1
                        lastPage = it.totalPages
                    }
            }
        }
    }*/
}