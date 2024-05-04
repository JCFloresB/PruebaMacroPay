package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote

import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.GetMoviesResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail.GetMovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @GET("movie/now_playing")
    suspend fun getMovies(): GetMoviesResponse

    @GET("/movie/{idMovie}")
    suspend fun getDetailMovie(@Path("idMovie") idMovie: Int): GetMovieDetailResponse
}