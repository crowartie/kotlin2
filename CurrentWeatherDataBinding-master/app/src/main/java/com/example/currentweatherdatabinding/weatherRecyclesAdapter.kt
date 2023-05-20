package com.example.currentweatherdatabinding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherRecyclesAdapter(private val ctx: Context, private val weatherDataArray:ArrayList<WeatherData>):
    RecyclerView.Adapter<WeatherRecyclesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textCity: TextView
        var textTemp: TextView
        var imageWeather: ImageView
        var nameImgWeather: TextView
        init{
            textCity= itemView.findViewById(R.id.city)
            textTemp=itemView.findViewById(R.id.temp)
            imageWeather = itemView.findViewById(R.id.ImageWeather)
            nameImgWeather = itemView.findViewById(R.id.nameImgWeather)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =LayoutInflater.from(ctx).inflate(R.layout.card_weather,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textCity.text=weatherDataArray[position].city
        holder.textTemp.text=weatherDataArray[position].temperature
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
        holder.nameImgWeather.text = weatherDataArray[position].weatherImg
    }

    override fun getItemCount(): Int {
        return weatherDataArray.size
    }
}