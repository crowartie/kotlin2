package com.example.fragmentsweather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentsweather.R
import com.example.fragmentsweather.adapters.AdapterBriefly

class weatherBriefly : Fragment() {
    private lateinit var weatherAdapter: AdapterBriefly
    private val dataModel: DataModel by activityViewModels()
    private lateinit var dividerItemDecoration: DividerItemDecoration

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherAdapter= AdapterBriefly((dataModel.data.value?:mutableListOf<WeatherData>()) as ArrayList<WeatherData>)
        view.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable_vertical))
            addItemDecoration(dividerItemDecoration)
            adapter = weatherAdapter
        }
        dataModel.data.observe(activity as LifecycleOwner) {
            weatherAdapter.updateData(ArrayList(it))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_briefly, container, false)
    }

    companion object {
        fun newInstance() =
            weatherBriefly()
    }
}