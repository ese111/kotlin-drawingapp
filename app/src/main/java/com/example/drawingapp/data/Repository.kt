package com.example.drawingapp.data

import com.example.drawingapp.InputFactory

interface Repository {

    fun getInputFactory(): InputFactory

    fun getRectangle(inputFactory: InputFactory): Rectangle

    fun getRectangleLog(rectangle: Rectangle): String

    fun setPlane(rectangle: Rectangle)

    fun getPlane(index: Int): Rectangle

    fun getPlaneCount(): Int

    fun setAlpha(index: Int, value: Int)

    fun getAlpha(index: Int): Int
}