package com.example.recyclerviewcolorsk

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // получаем ссылку на текстовое поле в каждом элементе списка
    val tv = itemView.findViewById<TextView>(R.id.color)

    // TODO: добавить обработку нажатия на элемент списка (вывести Toast с кодом цвета)
    // совет: использовать блок init { }
    private val ch = ColorDiffer

    init {
        tv.setOnClickListener {
            Toast.makeText(tv.context, tv.text, Toast.LENGTH_SHORT).show()
            val c = ch.getColor()
            tv.text = itemView.context.getString(R.string.template, c)
            tv.setBackgroundColor(c)
            bindTo(c)
        }

    }
    fun bindTo(color: Int) {
        tv.setBackgroundColor(color)
        // вывод кода цвета в 16-ричном виде
        tv.text = itemView.context.getString(R.string.template, color)
    }

}
