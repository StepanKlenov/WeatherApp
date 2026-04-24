package com.example.weatherapp.ui
import java.util.Properties
import androidx.lifecycle.*
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.domain.model.WeatherItem
import kotlinx.coroutines.launch
import android.util.Log

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weatherList = MutableLiveData<List<WeatherItem>>()
    val weatherList: LiveData<List<WeatherItem>> = _weatherList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadWeather(city: String) {
        Log.d("WEATHER_APP", "Button clicked, city = $city")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = ""

                val result = repository.getWeather(city)

                Log.d("WEATHER_APP", "Loaded items = ${result.size}")

                _weatherList.value = result
            } catch (e: Exception) {
                Log.e("WEATHER_APP", "Loading error", e)
                _error.value = e.message ?: "Ошибка загрузки"
            } finally {
                _isLoading.value = false
            }
        }
    }
}