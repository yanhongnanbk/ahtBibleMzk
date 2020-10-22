package com.yan.ahtbibleaudio002.views.adapters

import com.yan.ahtbibleaudio002.models.AudioItem

interface AudioAdapterListener {
    fun onShowDetails(audioItem: AudioItem)
}
