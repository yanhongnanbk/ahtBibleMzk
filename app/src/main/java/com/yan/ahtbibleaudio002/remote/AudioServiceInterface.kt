package com.yan.ahtbibleaudio002.remote

import com.yan.ahtbibleaudio002.models.AudioResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://demo6153500.mockable.io"

interface AudioServiceInterface {
    @GET("/audio")
    fun getAllAudio(): Call<AudioResponse>

    companion object {
        val instance: AudioServiceInterface by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create<AudioServiceInterface>(AudioServiceInterface::class.java)
        }
    }
}