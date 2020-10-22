package com.yan.ahtbibleaudio002.repositories

import android.util.Log
import com.yan.ahtbibleaudio002.models.AudioItem
import com.yan.ahtbibleaudio002.models.AudioResponse
import com.yan.ahtbibleaudio002.remote.AudioServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AudioRepository(private val audioServiceInterface: AudioServiceInterface) {
    fun getAllAudios(
        callBack: (List<AudioItem>?) -> Unit
    ) {
        val dataCall = audioServiceInterface.getAllAudio()
        dataCall.enqueue(object : Callback<AudioResponse> {
            override fun onFailure(
                call: Call<AudioResponse>?,
                t: Throwable?
            ) {
                Log.d("AudioRepository","onFailure")
                callBack(null)
            }

            override fun onResponse(
                call: Call<AudioResponse>?,
                response: Response<AudioResponse>?
            ) {
                Log.d("AudioRepository","onResponse")

                val body = response?.body()
                callBack(body?.data?.audioItems)
            }
        })
    }
}