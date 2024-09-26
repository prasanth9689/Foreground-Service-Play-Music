package com.skyblue.foregroundserviceplaymusic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MusicPlayerService: Service() {
    private val TAG = "MainActivity_"
    var isPlaying: Boolean = false
    var player: MediaPlayer? = null


    @Override
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null) {
            if (intent.action.equals("start_service")){
                val songName = intent.getStringExtra("name")
                val songUri = intent.getStringExtra("uri")

                if (player == null){
                    player = MediaPlayer.create(this, Uri.parse(songUri))
                }
                player!!.start()
                createNotification(songName);
            }else if (intent.action.equals("stop_service")){
                if (player != null) {
                    player!!.release()
                    player = null
                }
                isPlaying = false
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun createNotification(songName: String?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(
                "running_channel",
                "Running Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_music)
            .setContentTitle("Music Player")
            .setContentText(songName)
            .build()
        startForeground(1, notification)
    }

    override fun stopService(name: Intent?): Boolean {
        // Service Status : Service has been stopped
        return super.stopService(name)
    }

    override fun onDestroy() {
        // Service execution completed
        Log.d(TAG,"Stopped : Service Stopped")
        super.onDestroy()
    }

}