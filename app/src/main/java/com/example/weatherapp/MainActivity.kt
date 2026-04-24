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
    private lateinit var loadButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var currentWeatherCard: View
    private lateinit var currentIconTextView: TextView
    private lateinit var currentTempTextView: TextView
    private lateinit var currentDescTextView: TextView

    private lateinit var adapter: WeatherAdapter

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
        setupSpinner()
        setupRecycler()
        setupObservers()
        setupClick()
    }

    private fun initViews() {
        citySpinner = findViewById(R.id.citySpinner)
        loadButton = findViewById(R.id.loadWeatherButton)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorTextView)
        recyclerView = findViewById(R.id.weatherRecyclerView)

        currentWeatherCard = findViewById(R.id.currentWeatherCard)
        currentIconTextView = findViewById(R.id.currentIconTextView)
        currentTempTextView = findViewById(R.id.currentTempTextView)
        currentDescTextView = findViewById(R.id.currentDescTextView)
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities
        )

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = spinnerAdapter
    }

    private fun setupRecycler() {
        adapter = WeatherAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.weatherList.observe(this) { list ->
            adapter.updateData(list)

            val current = list.firstOrNull()
            if (current != null) {
                currentWeatherCard.visibility = View.VISIBLE
                currentIconTextView.text = current.icon
                currentTempTextView.text = current.temperature
                currentDescTextView.text = current.description
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            loadButton.isEnabled = !isLoading
        }

        viewModel.error.observe(this) { error ->
            if (error.isNullOrEmpty()) {
                errorText.visibility = View.GONE
            } else {
                errorText.text = error
                errorText.visibility = View.VISIBLE
                currentWeatherCard.visibility = View.GONE
            }
        }
    }

    private fun setupClick() {
        loadButton.setOnClickListener {
            val city = citySpinner.selectedItem.toString()
            viewModel.loadWeather(city)
        }
    }
}