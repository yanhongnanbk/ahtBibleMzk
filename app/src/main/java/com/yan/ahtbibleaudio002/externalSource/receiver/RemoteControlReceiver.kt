package com.yan.ahtbibleaudio002.externalSource.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.KeyEvent
import com.yan.ahtbibleaudio002.externalSource.helpers.NEXT
import com.yan.ahtbibleaudio002.externalSource.helpers.PLAYPAUSE
import com.yan.ahtbibleaudio002.externalSource.helpers.PREVIOUS
import com.yan.ahtbibleaudio002.externalSource.helpers.sendIntent


class RemoteControlReceiver : BroadcastReceiver() {
    companion object {
        private const val MAX_CLICK_DURATION = 700

        private var mContext: Context? = null
        private val mHandler = Handler()

        private var mClicksCnt = 0

        private val runnable = Runnable {
            if (mClicksCnt == 0)
                return@Runnable

            mContext!!.sendIntent(
                    when (mClicksCnt) {
                        1 -> PLAYPAUSE
                        2 -> NEXT
                        else -> PREVIOUS
                    }
            )
            mClicksCnt = 0
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        mContext = context
        if (intent.action == Intent.ACTION_MEDIA_BUTTON) {
            val event = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
            if (event?.action == KeyEvent.ACTION_UP) {
                when (event.keyCode) {
                    KeyEvent.KEYCODE_MEDIA_PLAY, KeyEvent.KEYCODE_MEDIA_PAUSE -> context.sendIntent(PLAYPAUSE)
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS -> context.sendIntent(PREVIOUS)
                    KeyEvent.KEYCODE_MEDIA_NEXT -> context.sendIntent(NEXT)
                    KeyEvent.KEYCODE_HEADSETHOOK -> {
                        mClicksCnt++

                        mHandler.removeCallbacks(runnable)
                        if (mClicksCnt >= 3) {
                            mHandler.post(runnable)
                        } else {
                            mHandler.postDelayed(runnable, MAX_CLICK_DURATION.toLong())
                        }
                    }
                }
            }
        }
    }
}

