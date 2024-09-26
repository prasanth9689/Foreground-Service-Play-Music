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

        Log.i(TAG, "Song Name : $songName" +
                "\nSong Uri : $songUri")

        binding.fabPlayOrPause.setOnClickListener {
            if (isPlaying) {
                isPlaying = false
                binding.fabPlayOrPause.setImageResource(R.drawable.ic_play)
            } else {

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


                /*
            if (isPlaying) {
                if (player != null){
                    player!!.pause()
                    isPlaying = false
                    binding.fabPlayOrPause.setImageResource(R.drawable.ic_play)
                }
            } else {
                if (player == null){

                    val serviceIntent = Intent(this, MusicPlayerService::class.java)
                    serviceIntent.putExtra("name", songName)
                    serviceIntent.action = "start_service"
                    startService(serviceIntent)

                    player = MediaPlayer.create(this, Uri.parse(songUri))
                }
                player!!.start()
                isPlaying = true
                with(binding) {
                    fabPlayOrPause.setImageResource(R.drawable.ic_pause)
                    fabStop.visibility = View.VISIBLE
                }

            }
             */
            }
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
}
