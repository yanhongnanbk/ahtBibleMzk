package com.yan.ahtbibleaudio002.externalSource.helpers

import android.content.Context
import android.content.Intent
import com.yan.ahtbibleaudio002.externalSource.services.MusicService
import java.text.SimpleDateFormat
import java.util.*




fun Context.sendIntent(action: String) {
    Intent(this, MusicService::class.java).apply {
        this.action = action
        try {
            startService(this)
        } catch (ignored: Exception) {
        }
    }
}


val Context.config: Config get() = Config.newInstance(applicationContext)

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}





