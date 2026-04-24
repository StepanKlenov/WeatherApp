package com.example.weatherapp.domain.model

data class WeatherItem(
    val date: String,
    val temperature: String,
    val icon: String,
    val description: String
)