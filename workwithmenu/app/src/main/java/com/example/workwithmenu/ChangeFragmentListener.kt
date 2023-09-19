package com.example.workwithmenu

import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import com.example.workwithmenu.fragments.WeatherBriefly
import com.example.workwithmenu.fragments.WeatherDetail

class ChangeFragmentListener(private val fragmentManager: FragmentManager): DialogInterface.OnClickListener {
    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            0 -> fragmentManager.beginTransaction()
                .replace(R.id.fragment, WeatherDetail.newInstance())
                .commit()
            1 -> fragmentManager.beginTransaction()
                .replace(R.id.fragment, WeatherBriefly.newInstance())
                .commit()
        }
    }

}