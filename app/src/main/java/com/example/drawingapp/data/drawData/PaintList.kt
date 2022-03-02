package com.example.drawingapp.data.drawData

import android.graphics.Paint

class PaintList {

    val paints = mutableListOf<Paint>()

    fun putPaint(paint: Paint) = paints.add(paint)
}