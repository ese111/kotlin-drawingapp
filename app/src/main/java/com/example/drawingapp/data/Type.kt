package com.example.drawingapp.data

import android.graphics.Rect
import com.example.drawingapp.data.input.InputType


interface Type {
    val rect: Rect
    val  type: InputType

    fun setAlpha(value: Int)

    fun getAlpha(): Int
}

