package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.movie

import androidx.paging.PagingData
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
)  {
    operator fun invoke(): Flow<PagingData<Movie>> =
        moviesRepository.getMovies()
}
