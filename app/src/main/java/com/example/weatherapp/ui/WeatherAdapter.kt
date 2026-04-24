package com.example.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.WeatherItem
import java.util.Properties
class WeatherAdapter(
    private var items: List<WeatherItem>
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val temperatureTextView: TextView = view.findViewById(R.id.temperatureTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)

        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = items[position]
        holder.dateTextView.text = item.date
        holder.temperatureTextView.text = item.temperature
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<WeatherItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}