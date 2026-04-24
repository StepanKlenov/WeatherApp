package com.example.weatherapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties
object ApiClient {

    private const val CITY_BASE_URL = "https://api.api-ninjas.com/"
    private const val WEATHER_BASE_URL = "https://api.open-meteo.com/"

    private val client = OkHttpClient.Builder()
        .build()

    val cityApi: CityApi by lazy {
        Retrofit.Builder()
            .baseUrl(CITY_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApi::class.java)
    }

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}