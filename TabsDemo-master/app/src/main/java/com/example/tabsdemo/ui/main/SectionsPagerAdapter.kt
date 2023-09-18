package com.example.tabsdemo.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tabsdemo.R
import com.example.tabsdemo.Weather


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val dataWeather: MutableList<Weather>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

            return PlaceholderFragment.newInstance(position + 1,
                dataWeather[position].name,
                dataWeather[position].temperature)

    }

    override fun getPageTitle(position: Int): CharSequence? {
        // TODO: задать заголовок - название города
        return context.resources.getStringArray(R.array.cities)[position]
    }

    override fun getCount(): Int {
        // TODO: возвращать число доступных городов
        return context.resources.getStringArray(R.array.cities).size
    }
}