package com.yan.ahtbibleaudio002.externalSource.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yan.ahtbibleaudio002.externalSource.helpers.*


class ControlActionsListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        when (action) {
            PREVIOUS, PLAYPAUSE, NEXT, FINISH -> context.sendIntent(action)
        }
    }
}

