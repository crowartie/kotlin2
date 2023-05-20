package com.example.currentweatherdatabinding

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.graphics.Insets.add
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private lateinit var adapter: RecyclerView.Adapter<WeatherRecyclesAdapter.ViewHolder>
    private lateinit var recyclerView: RecyclerView
    private lateinit var weatherDataArray: ArrayList<WeatherData>
    private lateinit var apiKey: String
    private lateinit var inputSearchCity: EditText
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.search)
        inputSearchCity = findViewById(R.id.input_search_city)
        apiKey = "7fd21e47dc11c2454d4f27cc033f607a"
        createAdapter()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    fun onClickCreateCardView(view: View){
        GlobalScope.launch (Dispatchers.IO) {
            check_connection()
        }
    }

@SuppressLint("UseCompatLoadingForDrawables")
fun createAdapter() {
    weatherDataArray = ArrayList()
    recyclerView = findViewById(R.id.recyclerView)
    layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    adapter = WeatherRecyclesAdapter(this,weatherDataArray)
    recyclerView.layoutManager = layoutManager
    dividerItemDecoration = DividerItemDecoration(this, RecyclerView.HORIZONTAL)
    dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
    recyclerView.addItemDecoration(dividerItemDecoration)
    recyclerView.adapter = adapter
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
            weatherDataArray.forEach{
                if(it.city==dates.name.toString()){
                    return@runOnUiThread
                }
            }
            weatherDataArray.add(WeatherData(dates.name.toString(), (dates.main!!.temp.toString().toFloat()-273.15).toInt().toString(),
                dates.weather[0].main.toString()))
            adapter.notifyDataSetChanged()
        }
    }
}