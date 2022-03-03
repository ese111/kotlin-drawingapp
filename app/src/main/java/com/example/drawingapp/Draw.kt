package com.example.drawingapp

import android.graphics.PointF
import com.example.drawingapp.data.Rectangle

interface Draw {
    fun drawRectangle(rectangle: Rectangle)

    fun getColor(index: Int): String

    fun findRectangle(pointF: PointF): Int

    fun strokeRectReset()

    fun onClickRectangleIndex(): Int
}