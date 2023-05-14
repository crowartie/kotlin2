package com.example.newcustomadapter

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStreamReader
import com.google.gson.Gson
class MainActivity : AppCompatActivity() {
    private lateinit var jsonUsers:Users
    private lateinit var adapter: UserListAdapter;
    private lateinit var listView: ListView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        val users: ArrayList<User> = ArrayList();
        val gson = Gson()
        jsonUsers = gson.fromJson(InputStreamReader(getAssets().open("output.json"), "UTF-8"), Users::class.java)
        // TODO: реализовать загрузку данных из JSON-файла
        // который загрузить в папку assets
        for(i in 0 until jsonUsers.users.size){
            println(jsonUsers.users.get(i))
            users.add(User(jsonUsers.users.get(i).name,jsonUsers.users.get(i).phoneNumber,jsonUsers.users.get(i).sex))

        }





        adapter = UserListAdapter(this, users);
        listView.setAdapter(adapter)
    }

    fun onClick_sort_for_name(view: View) {
        adapter.sort_name()
    }

    fun onClick_sort_for_phoneNumber(view: View) {
        adapter.sort_phoneNumber()
    }
    fun onClick_sort_for_sex(view: View) {
        adapter.sort_sex()

    }
}