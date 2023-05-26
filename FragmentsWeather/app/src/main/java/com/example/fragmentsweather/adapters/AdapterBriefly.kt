package com.example.fragmentsweather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentsweather.R
import com.example.fragmentsweather.fragments.WeatherData

class AdapterBriefly(private val weatherDataArray:ArrayList<WeatherData>):
    RecyclerView.Adapter<AdapterBriefly.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textCity: TextView
        var imageWeather: ImageView
        init{
            textCity= itemView.findViewById(R.id.city)
            imageWeather = itemView.findViewById(R.id.ImageWeather)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.card_weather_briefly,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textCity.text=weatherDataArray[position].city
        when(weatherDataArray[position].weatherImg){
            "Thunderstorm" -> holder.imageWeather.setImageResource(R.drawable.img11)
            "Drizzle" -> holder.imageWeather.setImageResource(R.drawable.img9)
            "Rain" -> holder.imageWeather.setImageResource(R.drawable.img10)
            "Snow" -> holder.imageWeather.setImageResource(R.drawable.img13)
            "Mist" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Smoke" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Haze" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Dust" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Fog" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Sand" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Ash" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Squall" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Tornado" -> holder.imageWeather.setImageResource(R.drawable.img50)
            "Clear" -> holder.imageWeather.setImageResource(R.drawable.img1)
            "Clouds" -> holder.imageWeather.setImageResource(R.drawable.img2)
        }
    }

    override fun getItemCount(): Int {
        return weatherDataArray.size
    }
    fun updateData(newData: ArrayList<WeatherData>){
        weatherDataArray.clear()
        weatherDataArray.addAll(newData)
        notifyDataSetChanged()
    }
}
