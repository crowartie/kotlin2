package com.example.tabsdemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    private val _city =MutableLiveData<String>()
    private val _temperature = MutableLiveData<String>()

    val text: LiveData<String> = Transformations.map(_index) {
        "Weather in ${_city.value}: ${_temperature.value}"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
    fun setName(name: String){
        _city.value = name
    }
    fun setTemperature(temperature: String){
        _temperature.value = temperature
    }
}