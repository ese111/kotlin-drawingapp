package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.Rect
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle

interface Contract {

    interface View {

        fun getDrawMessage(message: String)

        fun drawRectangle(rectangle: Rectangle)

        fun drawPicture(picture: Picture)

        fun setColorText(count: Int)

        fun onTouchRectangle(pointF: PointF)

        fun changeAlpha(type: Type, index: Int)

        fun setAlpha(index: Int, value: Int)
    }

    interface Presenter {

        fun getRectangleLog(): String

        fun onClickLog()

        fun getDrawRectangle(index: Int)
        
        fun getRectangle(): Rectangle

        fun getPicture(bitmap: Bitmap): Picture

        fun setAlpha(index: Int, value: Int)

        fun setPlane()

        fun setPlane(bitmap: Bitmap)

        fun getAlpha(index: Int): Int

        fun getDrawRectangle()

        fun getDrawPicture()

        fun getDrawPicture(index: Int)

        fun getInput(inputType: InputType): InputFactory

        fun setPlaneXY(index : Int, get: Rect?)
    }

}