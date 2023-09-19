package com.example.workwithmenu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workwithmenu.R
import com.example.workwithmenu.fragments.WeatherData

class AdapterBriefly(private val weatherDataArray: ArrayList<WeatherData>) :
    RecyclerView.Adapter<AdapterBriefly.ViewHolder>() {

    private val weatherImageMap = mapOf(
        "Thunderstorm" to R.drawable.img11,
        "Drizzle" to R.drawable.img9,
        "Rain" to R.drawable.img10,
        "Snow" to R.drawable.img13,
        "Mist" to R.drawable.img50,
        "Smoke" to R.drawable.img50,
        "Haze" to R.drawable.img50,
        "Dust" to R.drawable.img50,
        "Fog" to R.drawable.img50,
        "Sand" to R.drawable.img50,
        "Ash" to R.drawable.img50,
        "Squall" to R.drawable.img50,
        "Tornado" to R.drawable.img50,
        "Clear" to R.drawable.img1,
        "Clouds" to R.drawable.img2
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCity: TextView = itemView.findViewById(R.id.city)
        val imageWeather: ImageView = itemView.findViewById(R.id.ImageWeather)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.card_weather_briefly, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = weatherDataArray[position]
        holder.textCity.text = weatherData.city
        weatherImageMap[weatherData.weatherImg]?.let { holder.imageWeather.setImageResource(it) }
    }

    override fun getItemCount(): Int {
        return weatherDataArray.size
    }

    fun updateData(newData: ArrayList<WeatherData>) {
        weatherDataArray.clear()
        weatherDataArray.addAll(newData)
        notifyDataSetChanged()
    }
}