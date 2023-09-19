package com.example.musicplayerpart2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayerpart2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var musicService: MediaPlayerService? = null
    private var isBound = false
    private lateinit var playPauseButton:Button
    private lateinit var switchActivityButton:Button
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.LocalBinder
            musicService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Intent(this, MediaPlayerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        playPauseButton = findViewById(R.id.playPauseButton)
        playPauseButton.setOnClickListener {
            playMusic()
        }
        switchActivityButton = findViewById(R.id.switchActivityButton)

        switchActivityButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    fun playMusic() {
        if (isBound) {
            musicService?.playOnPause()
        }
    }


    // ... другие методы для управления музыкой ...
}