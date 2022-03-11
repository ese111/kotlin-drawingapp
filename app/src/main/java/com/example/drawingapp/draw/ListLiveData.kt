package com.example.drawingapp.draw

import androidx.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<MutableList<T>?>() {

    init {
        value = mutableListOf()
    }

    fun getList() = value?.toList()

    fun add(item: T) {
        val items: MutableList<T>? = value
        items!!.add(item)
        value = items
    }

}