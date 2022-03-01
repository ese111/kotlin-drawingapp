package com.example.drawingapp

import com.example.drawingapp.data.Rectangle

interface Contract {
    interface View {

        fun getDrawMessage(message: String)

    }

    interface Presenter {

        fun getDrawRectangle(): String

        fun onClickLog()

    }

    interface Repository {

        fun getInputFactory(): InputFactory

        fun setRectangleInfo(inputFactory: InputFactory): Rectangle

        fun getRectangleInfo(rectangle: Rectangle): String
    }
}