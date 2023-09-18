package com.example.tabsdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.tabsdemo.databinding.ActivityMainBinding
import com.example.tabsdemo.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    private val weatherData = mutableListOf<Weather>()
    private lateinit var binding: ActivityMainBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val cities = resources.getStringArray(R.array.cities)
        val api =resources.getString(R.string.API_KEY)

        coroutineScope.launch {
            for (city in cities) {
                val weather = withContext(Dispatchers.IO) {
                    GetWeather().getWeather(city, api)
                }
                weatherData.add(weather)
            }
            val sectionsPagerAdapter = SectionsPagerAdapter(this@MainActivity, supportFragmentManager, ArrayList(weatherData))
            val viewPager: ViewPager = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter

            val tabs: TabLayout = binding.tabs
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}

class GetWeather{
    fun getWeather(city: String, api: String): Weather {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${api}&units=metric"
        val weather = Weather()

        try {
            val stream = URL(url).openStream()
            val dataJson = Scanner(stream).nextLine() ?: ""

            val jsonObj = JSONObject(dataJson)
            weather.name= jsonObj.getString("name")
            weather.temperature = jsonObj.getJSONObject("main").getString("temp").let { "$it°" }

            stream.close()
        } catch (e: IOException) {
            weather.name = "Error Internet connection!"
            Log.e("GetWeather", "Error in network connection", e)
        } catch (e: Exception) {
            weather.name = "Некоректный вопрос"
            Log.e("GetWeather", "Error in parsing JSON", e)
        }

        return weather
    }
}

