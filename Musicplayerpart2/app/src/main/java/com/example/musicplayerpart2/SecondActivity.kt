package com.example.musicplayerpart2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {
    private var musicService: MediaPlayerService? = null
    private var isBound = false
    private lateinit var playPauseButton:Button
    private lateinit var switchActivityButton:Button

    private lateinit var progressBar:ProgressBar
    private lateinit var elapsedTimeTextView:TextView
    private lateinit var remainingTimeTextView:TextView
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.LocalBinder
            musicService = binder.getService()
            isBound = true
            progressBar.max = musicService?.getMediaPlayer()?.duration?.toLong()?.toInt()!!
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val mediaPlayer = musicService?.getMediaPlayer()
                    if (mediaPlayer != null) {
                        val currentPos= mediaPlayer.currentPosition.toLong()

                        val elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(currentPos)
                        val elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(currentPos) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPos))

                        val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes((mediaPlayer.duration - mediaPlayer.currentPosition).toLong())
                        val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds((mediaPlayer.duration - mediaPlayer.currentPosition).toLong()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((mediaPlayer.duration - mediaPlayer.currentPosition).toLong()))

                        elapsedTimeTextView.text = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds)
                        remainingTimeTextView.text = String.format("%02d:%02d", remainingMinutes, remainingSeconds)

                        progressBar.progress = mediaPlayer.currentPosition

                        handler.postDelayed(this, 1000)
                    }
                }
            }, 0)
        }


        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Intent(this, MediaPlayerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        playPauseButton = findViewById(R.id.playPauseButton)
        playPauseButton.setOnClickListener {
            playMusic()
        }
        switchActivityButton = findViewById(R.id.switchActivityButton)
        switchActivityButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        progressBar = findViewById(R.id.progressBar)
        elapsedTimeTextView = findViewById(R.id.elapsedTimeTextView)
        remainingTimeTextView = findViewById(R.id.remainingTimeTextView)


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
}