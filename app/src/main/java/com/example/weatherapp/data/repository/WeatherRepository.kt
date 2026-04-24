package com.example.weatherapp.data.repository

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.ApiClient
import com.example.weatherapp.domain.model.WeatherItem
import kotlin.math.roundToInt

class WeatherRepository {

    suspend fun getWeather(cityName: String): List<WeatherItem> {
        val cities = ApiClient.cityApi.getCityCoordinates(
            apiKey = BuildConfig.API_NINJAS_KEY,
            cityName = cityName
        )

        val city = cities.firstOrNull()
            ?: throw Exception("Город не найден")

        val weather = ApiClient.weatherApi.getWeatherForecast(
            latitude = city.latitude,
            longitude = city.longitude
        )

        val dates = weather.daily.time
        val maxTemps = weather.daily.temperature_2m_max
        val minTemps = weather.daily.temperature_2m_min
        val codes = weather.daily.weather_code

        return dates.indices.map { index ->
            WeatherItem(
                date = formatDate(dates[index]),
                temperature = "${minTemps[index].roundToInt()}° / ${maxTemps[index].roundToInt()}°",
                icon = getWeatherIcon(codes[index]),
                description = getWeatherDescription(codes[index])
            )
        }
    }

    private fun formatDate(date: String): String {
        val parts = date.split("-")
        return if (parts.size == 3) {
            "${parts[2]}.${parts[1]}"
        } else {
            date
        }
    }

    private fun getWeatherIcon(code: Int): String {
        return when (code) {
            0 -> "☀️"
            1, 2 -> "🌤️"
            3 -> "☁️"
            45, 48 -> "🌫️"
            51, 53, 55, 56, 57 -> "🌦️"
            61, 63, 65, 66, 67 -> "🌧️"
            71, 73, 75, 77 -> "❄️"
            80, 81, 82 -> "🌦️"
            95, 96, 99 -> "⛈️"
            else -> "🌡️"
        }
    }

    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Ясно"
            1 -> "Преимущественно ясно"
            2 -> "Переменная облачность"
            3 -> "Пасмурно"
            45, 48 -> "Туман"
            51, 53, 55 -> "Морось"
            56, 57 -> "Ледяная морось"
            61, 63, 65 -> "Дождь"
            66, 67 -> "Ледяной дождь"
            71, 73, 75 -> "Снег"
            77 -> "Снежные зёрна"
            80, 81, 82 -> "Ливень"
            95 -> "Гроза"
            96, 99 -> "Гроза с градом"
            else -> "Погода"
        }
    }
}