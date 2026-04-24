package com.example.weatherapp.data.remote

import com.example.weatherapp.data.dto.CityDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Properties
interface CityApi {

    @GET("v1/city")
    suspend fun getCityCoordinates(
        @Header("X-Api-Key") apiKey: String,
        @Query("name") cityName: String
    ): List<CityDto>
}