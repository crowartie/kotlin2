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
import com.example.workwithmenu.adapters.AdapterDetail

class WeatherDetail : Fragment() {
    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherAdapter = AdapterDetail(ArrayList(dataModel.data.value ?: mutableListOf()))
        view.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL).apply {
                setDrawable(resources.getDrawable(R.drawable.divider_drawable_horizontal))
            })
            adapter = weatherAdapter
        }
        dataModel.data.observe(viewLifecycleOwner) { weatherAdapter.updateData(ArrayList(it)) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherDetail()
    }
}