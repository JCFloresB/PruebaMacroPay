package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies


import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("maximum")
    val maximum: String,
    @SerializedName("minimum")
    val minimum: String
)