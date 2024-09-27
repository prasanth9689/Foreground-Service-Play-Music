package com.skyblue.foregroundserviceplaymusic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val TAG = "MainActivity_"
        val whichAction: String = p1?.action.toString()

        Log.i(TAG, "Action : $whichAction")

        if (whichAction.equals("stop")) {
            val serviceIntent = Intent(p0, MusicPlayerService::class.java)
            serviceIntent.action = "stop_service"
            p0?.startService(serviceIntent)
        }
    }
}