package com.example.drawingapp.data

import android.graphics.Rect


interface Type {
    val rect: Rect
    val  type: String

    fun setAlpha(value: Int)

    fun getAlpha(): Int
}

