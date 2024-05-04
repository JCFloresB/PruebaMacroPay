package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies


import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)