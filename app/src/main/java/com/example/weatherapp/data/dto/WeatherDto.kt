package com.example.weatherapp.data.dto

data class WeatherDto(
    val daily: DailyDto
)

data class DailyDto(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val weather_code: List<Int>
)