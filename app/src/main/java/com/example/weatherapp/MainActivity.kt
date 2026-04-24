package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.ui.WeatherAdapter
import com.example.weatherapp.ui.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var citySpinner: Spinner
    private lateinit var loadWeatherButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var weatherRecyclerView: RecyclerView

    private lateinit var weatherAdapter: WeatherAdapter

    private val cities = listOf(
        "Moscow",
        "Saint Petersburg",
        "Kazan",
        "Sochi",
        "Novosibirsk"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupCitySpinner()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun initViews() {
        citySpinner = findViewById(R.id.citySpinner)
        loadWeatherButton = findViewById(R.id.loadWeatherButton)
        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView)
    }

    private fun setupCitySpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter(emptyList())
        weatherRecyclerView.layoutManager = LinearLayoutManager(this)
        weatherRecyclerView.adapter = weatherAdapter
    }

    private fun setupObservers() {
        viewModel.weatherList.observe(this) { weather ->
            weatherAdapter.updateData(weather)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            if (error.isNullOrBlank()) {
                errorTextView.visibility = View.GONE
            } else {
                errorTextView.text = error
                errorTextView.visibility = View.VISIBLE
            }
        }
    }

    private fun setupClickListeners() {
        loadWeatherButton.setOnClickListener {
            val selectedCity = citySpinner.selectedItem.toString()
            viewModel.loadWeather(selectedCity)
        }
    }
}