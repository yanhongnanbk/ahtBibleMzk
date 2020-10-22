package com.yan.ahtbibleaudio002.externalSource.receiver

import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.ActivityManager
import androidx.legacy.content.WakefulBroadcastReceiver
import com.yan.ahtbibleaudio002.externalSource.helpers.config
import com.yan.ahtbibleaudio002.externalSource.services.MusicService


public class TurnOffServiceReceiver : WakefulBroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.getAction().equals("STOP_TEST_SERVICE")) {
            context!!.config.alarm_turn_off = false
            if (isMyServiceRunning(context, MusicService::class.java)) {
                Toast.makeText(context, "Service is running!! Stopping...", Toast.LENGTH_LONG).show()
                context.stopService(Intent(context, MusicService::class.java))
            } else {
                Toast.makeText(context, "Service not running", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}