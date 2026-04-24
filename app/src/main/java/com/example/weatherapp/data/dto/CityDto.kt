package com.example.weatherapp.data.dto
import java.util.Properties

data class CityDto(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String
)