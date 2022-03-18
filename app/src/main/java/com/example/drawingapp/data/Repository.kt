package com.example.drawingapp.data

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.example.drawingapp.data.input.InputType

interface Repository {
    val plane: Plane

    fun getPlane(index: Int): Type?

    fun getPlaneCount(): Int?

    fun setAlpha(index: Int, value: Int)

    fun getAlpha(index: Int): Int?

    fun setPlaneXY(typeList: List<Type>)

    fun resetClick()

    fun setClick(index: Int)

    fun setRectangleInPlane()

    fun setPictureInPlane(bitmap: Bitmap)

    fun getLastPlane(): Type?

    fun setTextInPlane()
}