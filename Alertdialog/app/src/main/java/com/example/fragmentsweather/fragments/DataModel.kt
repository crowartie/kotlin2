package com.example.fragmentsweather.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataModel:ViewModel() {
    val data: MutableLiveData<MutableList<WeatherData>> by lazy {
        MutableLiveData (mutableListOf())
    }
}
data class WeatherData(val city: String, val temperature: String,val weatherImg: String){

}