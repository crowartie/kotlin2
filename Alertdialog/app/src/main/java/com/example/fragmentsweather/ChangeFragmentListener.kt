package com.example.fragmentsweather

import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import com.example.fragmentsweather.fragments.weatherBriefly
import com.example.fragmentsweather.fragments.weatherDetail

class ChangeFragmentListener(private val fragmentManager: FragmentManager): DialogInterface.OnClickListener {
    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            0 -> fragmentManager.beginTransaction()
                .replace(R.id.fragment, weatherDetail.newInstance())
                .commit()
            1 -> fragmentManager.beginTransaction()
                .replace(R.id.fragment, weatherBriefly.newInstance())
                .commit()
        }
    }

}