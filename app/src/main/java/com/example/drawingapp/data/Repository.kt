package com.example.drawingapp.data

import android.graphics.Bitmap
import com.example.drawingapp.InputFactory
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.example.drawingapp.data.input.InputType

interface Repository {

    fun getRectangle(inputFactory: InputFactory): Rectangle

    fun getRectangleLog(type: Type): String

    fun setPlane(type: Type)

    fun getPlane(index: Int): Type

    fun getPlaneCount(): Int

    fun setAlpha(index: Int, value: Int)

    fun getAlpha(index: Int): Int

    fun getPicture(inputFactory: InputFactory, bitmap: Bitmap): Picture

    fun getInputFactory(type: InputType): InputFactory
}