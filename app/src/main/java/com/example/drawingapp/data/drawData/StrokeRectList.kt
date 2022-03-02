package com.example.drawingapp.data.drawData

import android.graphics.Rect

class StrokeRectList {

    val strokeRects = mutableListOf<Rect>()

    fun putStrokeRect(strokeRect: Rect) = strokeRects.add(strokeRect)

}