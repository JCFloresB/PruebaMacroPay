package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.moviedetail


import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)