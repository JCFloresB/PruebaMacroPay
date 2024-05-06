package com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository

import androidx.paging.PagingData
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail.GetMovieDetailResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<PagingData<Movie>>

    fun getMovieDetail(movieId: Int): Flow<Result<GetMovieDetailResponse>>

}
