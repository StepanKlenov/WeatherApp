package com.example.weatherapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var citySpinner: Spinner
    private lateinit var loadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        citySpinner = findViewById(R.id.citySpinner)
        loadButton = findViewById(R.id.loadWeatherButton)

        val cities = listOf(
            "Moscow",
            "Saint Petersburg",
            "Kazan",
            "Sochi",
            "Novosibirsk"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter

        loadButton.setOnClickListener {
            val selectedCity = citySpinner.selectedItem.toString()
            Toast.makeText(this, "Выбран город: $selectedCity", Toast.LENGTH_SHORT).show()
        }
    }
}