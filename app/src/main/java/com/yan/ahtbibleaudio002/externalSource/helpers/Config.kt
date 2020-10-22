package com.yan.ahtbibleaudio002.externalSource.helpers

import android.content.Context

class Config(val context: Context)  {
    protected val prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        fun newInstance(context: Context) = Config(context)
    }

    var isShuffleEnabled: Boolean
        get() = prefs.getBoolean(SHUFFLE, false)

        set(shuffle) = prefs!!.edit().putBoolean(SHUFFLE, shuffle).apply()

    var equalizer: Int
        get() = prefs.getInt(EQUALIZER, 0)
        set(equalizer) = prefs.edit().putInt(EQUALIZER, equalizer).apply()

    var repeatSong: Boolean
        get() = prefs.getBoolean(REPEAT_SONG, false)
        set(repeat) = prefs.edit().putBoolean(REPEAT_SONG, repeat).apply()

    var autoplay: Boolean
        get() = prefs.getBoolean(AUTOPLAY, true)
        set(autoplay) = prefs.edit().putBoolean(AUTOPLAY, autoplay).apply()

    var alarm_turn_off: Boolean
        get() = prefs.getBoolean(ALARM_TURN_OFF, false)
        set(alarm_turn_off) = prefs.edit().putBoolean(ALARM_TURN_OFF, alarm_turn_off).apply()


    fun getNumberClick():Int {
        return prefs.getInt("key_click",0)
    }

    fun setNumberClick(value:Int){
        return prefs.edit().putInt("key_click",value).apply()
    }









}
