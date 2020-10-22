package com.yan.ahtbibleaudio002.models

import com.google.gson.annotations.SerializedName

data class AudioResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)