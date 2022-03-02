package com.example.drawingapp.data.drawData

import android.graphics.Rect

class RectList {
    val rects = mutableListOf<Rect>()

    fun putRect(rect: Rect) = rects.add(rect)

}