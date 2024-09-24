package com.skyblue.foregroundserviceplaymusic

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicPlayerService: Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.tom_and_jerry_theme)
        mediaPlayer.isLooping = true
    }

    fun startMusic(){
        if (!mediaPlayer.isPlaying){
            mediaPlayer.start()
        }
    }
    fun pauseMusic(){
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}