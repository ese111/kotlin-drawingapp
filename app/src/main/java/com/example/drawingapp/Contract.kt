package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import com.example.drawingapp.data.Plane
import com.example.drawingapp.data.Repository
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle

interface Contract {

    interface View {

        fun drawMessage(message: String)

        fun drawRectangle(rectangle: Rectangle)

        fun drawPicture(picture: Picture)

        fun setColorText(count: Int)

        fun onTouchRectangle(pointF: PointF)

        fun setAlpha(index: Int, value: Int)

        fun changeAlpha(index: Int, alpha: Int)

        fun setSideBar(count: Int)
    }

    interface Presenter {

        fun plane() : Plane

        fun onClickLog()

        fun setAlpha(index: Int, value: Int)

        fun getAlpha(index: Int): Int?

        fun setPlaneXY(typeList: List<Type>)

        fun drawPicture()

        fun drawRectangle()

        fun resetClick()

        fun setClick(index: Int)

        fun setPictureInPlane(bitmap: Bitmap)

        fun setRectangleInPlane()

    }

}