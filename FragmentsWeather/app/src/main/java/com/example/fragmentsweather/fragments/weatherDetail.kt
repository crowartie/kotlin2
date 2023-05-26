package com.example.fragmentsweather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentsweather.R
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentsweather.adapters.AdapterDetail

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [weatherBriefly.newInstance] factory method to
 * create an instance of this fragment.
 */
class weatherDetail : Fragment() {
    private lateinit var weatherAdapter: AdapterDetail
    private val dataModel: DataModel by activityViewModels()
    private lateinit var dividerItemDecoration: DividerItemDecoration

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherAdapter= AdapterDetail((dataModel.data.value?:mutableListOf<WeatherData>()) as ArrayList<WeatherData>)
        view.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dividerItemDecoration = DividerItemDecoration(context, RecyclerView.HORIZONTAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable_horizontal))
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
        return inflater.inflate(R.layout.fragment_weather_detail, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment weatherBriefly.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance()=
            weatherDetail()
    }
}