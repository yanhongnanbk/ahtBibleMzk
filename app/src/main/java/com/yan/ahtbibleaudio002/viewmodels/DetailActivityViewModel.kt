package com.yan.ahtbibleaudio002.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yan.ahtbibleaudio002.models.AudioItem

class DetailActivityViewModel : ViewModel() {
    private var mediaList: MutableLiveData<ArrayList<AudioItem>> = MutableLiveData()
    private var selectedArticle = MutableLiveData<AudioItem>()
    private var selectNumber = MutableLiveData<Int>()
    lateinit var model: DetailActivityViewModel
    fun getSelectedMedia(): MutableLiveData<AudioItem> {
        return selectedArticle
    }

    fun setSelectNumber(number: Int) {
        selectNumber.value = number
    }

    fun getSelectNUmber(): MutableLiveData<Int> {
        return selectNumber
    }


    fun setSelectedMedia(article: AudioItem) {
        selectedArticle.setValue(article)
    }

    fun getMediaList(): MutableLiveData<ArrayList<AudioItem>> {
        return mediaList
    }

    fun loadMediaItems(list: ArrayList<AudioItem>) {
        // fetch media here
        mediaList.value = list

    }
}