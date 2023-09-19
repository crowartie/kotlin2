package com.example.musicplayerpart1

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val playPauseButton = findViewById<Button>(R.id.playPauseButton)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val elapsedTimeTextView = findViewById<TextView>(R.id.elapsedTimeTextView)
        val remainingTimeTextView = findViewById<TextView>(R.id.remainingTimeTextView)
        val switchActivityButton = findViewById<Button>(R.id.switchActivityButton)

        switchActivityButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        mediaPlayer = (application as MyApplication).mediaPlayer

        progressBar.max = mediaPlayer.duration

        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                val elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.currentPosition.toLong())
                val elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.currentPosition.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.currentPosition.toLong()))

                val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes((mediaPlayer.duration - mediaPlayer.currentPosition).toLong())
                val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds((mediaPlayer.duration - mediaPlayer.currentPosition).toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((mediaPlayer.duration - mediaPlayer.currentPosition).toLong()))

                elapsedTimeTextView.text = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds)
                remainingTimeTextView.text = String.format("%02d:%02d", remainingMinutes, remainingSeconds)

                progressBar.progress = mediaPlayer.currentPosition

                handler.postDelayed(this, 1000)
            }
        }, 0)
    }
}