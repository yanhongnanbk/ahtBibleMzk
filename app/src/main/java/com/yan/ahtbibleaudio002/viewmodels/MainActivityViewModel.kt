package com.yan.ahtbibleaudio002.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yan.ahtbibleaudio002.models.AudioItem
import com.yan.ahtbibleaudio002.repositories.AudioRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    var audioRepository: AudioRepository? = null

    // Unit similar to void in java
    fun getAllAudios(callback: (List<AudioItem>) -> Unit) {
        audioRepository?.getAllAudios() { results ->
            if (results == null) {
                callback(emptyList())
            } else {
                callback(results)
            }
        }
    }

}