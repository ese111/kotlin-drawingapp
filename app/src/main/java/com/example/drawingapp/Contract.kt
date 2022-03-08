package com.example.drawingapp

import android.graphics.Canvas
import android.graphics.PointF
import com.example.drawingapp.data.Rectangle

interface Contract {

    interface View {

        fun getDrawMessage(message: String)

        fun drawRectangle(rectangle: Rectangle)

        fun setColorText(count: Int)

        fun onTouchRectangle(pointF: PointF)

        fun changeAlpha(rectangle: Rectangle, index: Int)

        fun setAlpha(index: Int, value: Int)
    }

    interface Presenter {

        fun getRectangleLog(): String

        fun onClickLog()

        fun getDrawRectangle(index: Int)

        fun getInput(): InputFactory

        fun getRectangle(): Rectangle

        fun setAlpha(index: Int, value: Int)

        fun setPlane()

        fun getAlpha(index: Int): Int

        fun getDrawRectangle()

    }

}