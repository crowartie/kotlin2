package com.example.workwithmenu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workwithmenu.R
import com.example.workwithmenu.adapters.AdapterBriefly

class WeatherBriefly : Fragment() {
    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_briefly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherAdapter = AdapterBriefly(ArrayList(dataModel.data.value ?: mutableListOf()))
        view.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL).apply {
                setDrawable(resources.getDrawable(R.drawable.divider_drawable_vertical))
            })
            adapter = weatherAdapter
        }
        dataModel.data.observe(viewLifecycleOwner) { weatherAdapter.updateData(ArrayList(it)) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherBriefly()
    }
}