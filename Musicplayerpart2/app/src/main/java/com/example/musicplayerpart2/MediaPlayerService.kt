package com.example.musicplayerpart2

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MediaPlayerService(): Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var _isPlaying: Boolean = false

    inner class LocalBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        mediaPlayer = MediaPlayer().apply {
            val afd = assets.openFd("file.mp3")
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            prepare()
        }
        return LocalBinder()
    }

    fun playOnPause() {
        _isPlaying = if (_isPlaying) {
            mediaPlayer?.pause()

            false
        } else{
            mediaPlayer?.start()
            true
        }
    }
    fun getCurrentPosition(): Long? {
        return mediaPlayer?.currentPosition?.toLong()
    }
    fun getMediaPlayer(): MediaPlayer? {
        return mediaPlayer
    }
    companion object {

        val TRACK_NAME = MutableLiveData<String>()
        val isPlaying = MutableLiveData<Boolean>()

        private val liveDataDuration = MutableLiveData(0)
        private val liveDataNowPosition = MutableLiveData(0)


        fun getLiveDataDuration(): MutableLiveData<Int> {
            return liveDataDuration
        }
        fun getLiveDataNowPosition(): LiveData<Int> {
            return liveDataNowPosition
        }


    }







}