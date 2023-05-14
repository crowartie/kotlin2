package com.example.newcustomadapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class UserListAdapter(private val ctx:Context, private var users: ArrayList<User>) : BaseAdapter() {

    override fun getCount(): Int {
        return users.size;
    }

    override fun getItem(position: Int): Any {
        return users.get(position);
    }

    override fun getItemId(position: Int):Long{
        return position.toLong();
    }
    fun sort_name(){
        users.sortBy { it.name }
        notifyDataSetChanged();
    }
    fun sort_phoneNumber(){
        users.sortBy { it.phoneNumber }
        notifyDataSetChanged();
    }
    fun sort_sex(){
        users.sortBy { it.sex }
        notifyDataSetChanged();
    }
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val u:User = users.get(position)

        val convertView = LayoutInflater.from(ctx).inflate(R.layout.useritem,parent,false)
        val ivUserpic: ImageView = convertView.findViewById(R.id.userpic)
        ivUserpic.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v:View) {
                v.setBackgroundColor(Color.RED)
            }

        });
        val tvName: TextView = convertView.findViewById(R.id.name)
        val tvPhone: TextView = convertView.findViewById(R.id.phone)

        tvName.setText(u.name)
        tvPhone.setText(u.phoneNumber)
        when(u.sex){
            "MAN" -> ivUserpic.setImageResource(R.drawable.user_man)
            "WOMAN" -> ivUserpic.setImageResource(R.drawable.user_woman)
            "UNKNOWN" -> ivUserpic.setImageResource(R.drawable.user_unknown)
        }
        return convertView
    }
}