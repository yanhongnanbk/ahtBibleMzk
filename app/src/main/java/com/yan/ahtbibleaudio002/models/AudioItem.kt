package com.yan.ahtbibleaudio002.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AudioItem(
    @SerializedName("category")
    val category: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("file_url")
    val fileUrl: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("title")
    val title: String?
) : Serializable