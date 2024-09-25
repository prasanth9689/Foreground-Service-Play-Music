package com.skyblue.foregroundserviceplaymusic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MusicPlayerService: Service() {
    private val TAG = "MainActivity_"

    @Override
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songName = intent?.getStringExtra("music_name")

        // Service has started running in the background
        if (intent != null){
            Log.d(TAG, "Service Name: $songName")
        }

        // Service Status : Service has been started
        for (i in 1..10){
            Thread.sleep(100)
            Log.d(TAG, "Service is running: $i")
        }
        createNotification(songName);

        stopSelf()
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
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Run is active")
            .setContentText(songName)
            .build()
        startForeground(1234, notification)
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