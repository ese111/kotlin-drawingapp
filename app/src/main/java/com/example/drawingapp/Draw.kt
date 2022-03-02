package com.example.drawingapp

import com.example.drawingapp.data.Rectangle

interface Draw {
    fun drawRectangle(rectangle: Rectangle)

    fun getColor()
}