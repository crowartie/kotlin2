package com.example.workwithmenu.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataModel : ViewModel() {
    // LiveData для хранения списка данных о погоде
    val data: MutableLiveData<MutableList<WeatherData>> by lazy {
        MutableLiveData(mutableListOf())
    }
}

// Класс данных для хранения информации о погоде
data class WeatherData(
    val city: String, // Название города
    val temperature: String, // Температура в городе
    val weatherImg: String // Ссылка на изображение погоды
)
