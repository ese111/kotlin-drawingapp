package com.example.drawingapp.data

import android.graphics.Point
import android.graphics.Rect
import com.example.drawingapp.data.attribute.Size
import com.example.drawingapp.data.input.InputType

interface Type {
    val number: Int
    val rect: Rect
    val point: Point
    val size: Size
    val type: InputType
    var click: Boolean
    var alpha: Int
    fun deepCopy(): Type
}

