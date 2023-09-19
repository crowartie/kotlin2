package ru.samsung.itacademy.mdev.getusdrate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(context: Context, private val data: List<String>) : ArrayAdapter<String>(context, R.layout.spinner_item, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.text)
        textView.text = data[position]
        return view
    }
}