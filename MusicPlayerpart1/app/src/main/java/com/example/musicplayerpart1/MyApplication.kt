package com.example.musicplayerpart1

import android.app.Application
import android.media.MediaPlayer

class MyApplication : Application() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer().apply {
            val afd = assets.openFd("file.mp3")
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            prepare()
        }
    }
}