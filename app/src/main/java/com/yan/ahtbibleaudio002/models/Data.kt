package com.yan.ahtbibleaudio002.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("audios")
    val audioItems: List<AudioItem>,
    @SerializedName("pagination")
    val pagination: Pagination
)