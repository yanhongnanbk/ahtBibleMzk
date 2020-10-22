package com.yan.ahtbibleaudio002.views.adapters

import com.yan.ahtbibleaudio002.models.AudioItem

interface ItemClickListener {
    fun onItemClickListener(audioItemItem: AudioItem, position: Int)
}