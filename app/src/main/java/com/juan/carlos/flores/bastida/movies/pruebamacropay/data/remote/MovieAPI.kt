package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote

import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.GetMoviesResponse
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail.GetMovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/now_playing?")
    suspend fun getMovies(@Query("page") page: Int): GetMoviesResponse

    @GET("/3/movie/{idMovie}")
    suspend fun getDetailMovie(@Path("idMovie") idMovie: Int): GetMovieDetailResponse
}