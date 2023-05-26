package com.example.fragmentsweather

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.fragmentsweather.databinding.ActivityMainBinding
import androidx.activity.viewModels
import com.example.fragmentsweather.fragments.DataModel
import com.example.fragmentsweather.fragments.WeatherData
import com.example.fragmentsweather.fragments.weatherBriefly
import com.example.fragmentsweather.fragments.weatherDetail
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var buttonViewWeather: Button
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiKey: String
    private lateinit var inputSearchCity: EditText
    private var viewBriefly=true
    private var ArrayListWeather = mutableListOf<WeatherData>()
    private val weatherInfoList: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonViewWeather=findViewById(R.id.viewWeather)
        inputSearchCity=findViewById(R.id.input_search_city)
        apiKey="7fd21e47dc11c2454d4f27cc033f607a"
        check_view_weather()

    }
    @OptIn(DelicateCoroutinesApi::class)
    fun onClickCreateCardView(view: View){
        GlobalScope.launch (Dispatchers.IO) {
            check_connection()
        }
    }

    fun check_view_weather(){

        if(viewBriefly==true){
            apply_view_briefly_weather()
            viewBriefly=false
        }
        else{
            apply_view_detail_weather()
            viewBriefly=true
        }
    }

    fun apply_view_briefly_weather(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, weatherBriefly.newInstance())
            .commit()
        buttonViewWeather.text="Подробнее"
    }
    fun apply_view_detail_weather(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, weatherDetail.newInstance())
            .commit()
        buttonViewWeather.text="Скрыть"
    }

    fun onChangeViewWeather(view: View) {
        check_view_weather()
    }

    suspend fun check_connection(){
        try{
            val weatherURL="https://api.openweathermap.org/data/2.5/weather?q=" +
                    inputSearchCity.text.toString() +
                    "&appid=" +
                    apiKey
            inputSearchCity.setText("")
            val connection = withContext(Dispatchers.IO){
                URL(weatherURL).openConnection()
            } as HttpURLConnection
            if (connection.responseCode == HttpURLConnection.HTTP_OK){
                load_weather(weatherURL)
            }
        }
        catch (e: IOException){
            return
        }
        catch (e: UnknownHostException){
            return
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private suspend fun load_weather(weatherURL: String){
        val stream = withContext(Dispatchers.IO) {
            URL(weatherURL).content
        } as InputStream
        val data = Scanner(stream).nextLine()
        val gson = Gson()
        val dates = gson.fromJson(data.toString(), allDataWeather::class.java)
        this@MainActivity.runOnUiThread {
            ArrayListWeather.forEach{
                if(it.city==dates.name.toString()){
                    return@runOnUiThread
                }
            }
            ArrayListWeather.add(WeatherData(dates.name.toString(), (dates.main!!.temp.toString().toFloat()-273.15).toInt().toString(),
                dates.weather[0].main.toString()))
            weatherInfoList.data.value=ArrayListWeather
        }
    }
}