package com.example.drawingapp

import android.graphics.Bitmap
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

        fun getDrawMessage(message: String)

        fun drawRectangle(rectangle: Rectangle)

        fun drawPicture(picture: Picture)

        fun setColorText(count: Int)

        fun onTouchRectangle(pointF: PointF)

        fun setAlpha(index: Int, value: Int)

        fun changeAlpha(index: Int, alpha: Int)
    }

    interface Presenter {

        fun plane() : Plane

        fun getRectangleLog(): String

        fun onClickLog()

        fun getRectangle(): Rectangle

        fun getPicture(bitmap: Bitmap): Picture

        fun setAlpha(index: Int, value: Int)

        fun setPlane()

        fun setPlane(bitmap: Bitmap)

        fun getAlpha(index: Int): Int?

        fun getInput(inputType: InputType): InputFactory

        fun setPlaneXY(typeList: List<Type>)

        fun drawPicture()

        fun drawRectangle()

        fun resetClick()

        fun setClick(index: Int)

    }

}