package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import com.example.drawingapp.data.Plane
import com.example.drawingapp.data.Repository
import com.example.drawingapp.data.Text
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle

interface Contract {

    interface View {

        fun drawMessage(message: String)

        fun setColorText(count: Int)

        fun onTouchRectangle(pointF: PointF)

        fun setAlpha(index: Int, value: Int)

        fun changeAlpha(index: Int, alpha: Int)

        fun setSideBar()

        fun addDrawList()
    }

    interface Presenter {

        fun plane() : Plane

        fun onClickLog()

        fun setAlpha(index: Int, value: Int)

        fun getAlpha(index: Int): Int?

        fun setPlaneXY(typeList: List<Type>)

        fun resetClick()

        fun setClick(index: Int)

        fun setPictureInPlane(bitmap: Bitmap)

        fun setRectangleInPlane()

        fun setTextInPlane()

        fun drawPicture(drawPicture: (Picture) -> Unit)

        fun drawRectangle(draw: (Rectangle) -> Unit)

        fun drawText(draw: (Text) -> Unit)
        fun getLastRectangle(): Type?
    }

}