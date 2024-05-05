package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.MovieAPI
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.MoviePaging
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail.GetMovieDetailResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.GetMoviesResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviePaging: Pager<Int, Movie>,
    private val moviesApi: MovieAPI
): MoviesRepository {
    override fun getMovies(): Flow<PagingData<Movie>> {
        return moviePaging.flow.map { paginData ->
            paginData.map { it }
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<Result<GetMovieDetailResponse>> = flow {
        emit(Result.Loading())
        try {
            val movieDetail = moviesApi.getDetailMovie(movieId)
            emit(Result.Success(movieDetail))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }
}