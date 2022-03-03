package com.example.drawingapp

import com.example.drawingapp.data.Rectangle

interface Contract {
    interface View {

        fun getDrawMessage(message: String)

        fun drawRectangle(rectangle: Rectangle)

        fun setColorText(count: Int)
    }

    interface Presenter {

        fun getRectangleLog(): String

        fun onClickLog()

        fun onClickDraw()

        fun getInput(): InputFactory

        fun getRectangle(): Rectangle

        fun setPlane()
    }

    interface Repository {

        fun getInputFactory(): InputFactory

        fun getRectangle(inputFactory: InputFactory): Rectangle

        fun getRectangleLog(rectangle: Rectangle): String

        fun setPlane(rectangle: Rectangle)

        fun getPlane(index: Int): Rectangle

        fun getPlaneCount(): Int
    }
}