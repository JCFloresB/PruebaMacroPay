package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.usecases.movie

import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail.GetMovieDetailResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
)  {
    operator fun invoke(movieId: Int): Flow<Result<GetMovieDetailResponse>> =
        moviesRepository.getMovieDetail(movieId)
}