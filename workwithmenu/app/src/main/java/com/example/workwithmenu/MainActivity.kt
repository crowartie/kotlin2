package com.example.workwithmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.workwithmenu.databinding.ActivityMainBinding
import com.example.workwithmenu.fragments.DataModel
import com.example.workwithmenu.fragments.WeatherBriefly
import com.example.workwithmenu.fragments.WeatherData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import java.util.Locale
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    private lateinit var designFragmentDialog: FragmentDialog
    private lateinit var buttonViewWeather: Button
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputSearchCity: EditText
    private val viewBriefly = true
    private val weatherInfoList: DataModel by viewModels()
    private val ArrayListWeather = mutableListOf<WeatherData>()
    private val apiKey = "7fd21e47dc11c2454d4f27cc033f607a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        designFragmentDialog = FragmentDialog()
        buttonViewWeather = findViewById(R.id.viewWeather)
        inputSearchCity = findViewById(R.id.input_search_city)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, WeatherBriefly.newInstance())
            .commit()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_change_locale, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_language_ru -> {
                setLocale("ru")
                Toast.makeText(this, "Выбран русский язык", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_language_en -> {
                setLocale("en")
                Toast.makeText(this, "Выбран английский язык", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onClickCreateCardView(view: View) {
        lifecycleScope.launch(Dispatchers.IO) {
            checkConnection()
        }
    }

    fun onChangeViewWeather(view: View) {
        designFragmentDialog.show(supportFragmentManager, null)
    }

    private suspend fun checkConnection() {
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=" +
                inputSearchCity.text.toString() +
                "&appid=" +
                apiKey
        inputSearchCity.setText("")
        try {
            val connection = withContext(Dispatchers.IO) {
                URL(weatherURL).openConnection() as HttpURLConnection
            }
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                loadWeather(weatherURL)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(this, "Ошибка ввода-вывода: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(this, "Неизвестный хост: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun loadWeather(weatherURL: String) {
        val stream = withContext(Dispatchers.IO) {
            URL(weatherURL).openStream()
        }
        val data = Scanner(stream).useDelimiter("\\A").next()
        val dates = Gson().fromJson(data, AllDataWeather::class.java)
        runOnUiThread {
            ArrayListWeather.forEach { if (it.city == dates.name.toString()) return@runOnUiThread }
            ArrayListWeather.add(WeatherData(dates.name.toString(), (dates.main!!.temp?.minus(273.15))?.toInt()
                .toString(), dates.weather[0].main.toString()))
            weatherInfoList.data.value = ArrayListWeather
        }
    }
}