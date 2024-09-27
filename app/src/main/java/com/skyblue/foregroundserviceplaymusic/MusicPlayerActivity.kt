package com.skyblue.foregroundserviceplaymusic

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.skyblue.foregroundserviceplaymusic.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicPlayerBinding
    private val TAG = "MusicPlayerActivity_"
    private var context = this@MusicPlayerActivity
    var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val songName = intent.getStringExtra("name")
        val  songUri = intent.getStringExtra("uri")

        playSong(songName, songUri)

        Log.i(TAG, "Song Name : $songName" +
                "\nSong Uri : $songUri")

        binding.fabPlayOrPause.setOnClickListener {
                playSong(songName, songUri)
        }

        binding.fabStop.setOnClickListener {
            val serviceIntent = Intent(this, MusicPlayerService::class.java)
            serviceIntent.putExtra("name", songName)
            serviceIntent.action = "stop_service"
            startService(serviceIntent)

            binding.fabStop.visibility = View.GONE
            binding.fabPlayOrPause.setImageResource(R.drawable.ic_play)
        }
    }

    private fun playSong(songName: String?, songUri: String?) {
        val serviceIntent = Intent(this, MusicPlayerService::class.java)
        serviceIntent.putExtra("name", songName)
        serviceIntent.putExtra("uri", songUri)
        serviceIntent.action = "start_service"
        startService(serviceIntent)

        isPlaying = true
        with(binding) {
            fabPlayOrPause.setImageResource(R.drawable.ic_pause)
            fabStop.visibility = View.VISIBLE
        }
    }

}
