package com.example.drawingapp.data

import com.example.drawingapp.InputFactory
import com.example.drawingapp.util.generateRandom

class RectangleRepository {
    private var count = 1
    private val rectangleId: RectangleId = RectangleId()

    private fun putId(randomId: String = generateRandom()) {
        while (true) {
            val id = rectangleId.makeRandomId(randomId)
            if(rectangleId.checkId(id)) {
                rectangleId.putId(id)
                count++
                return
            }
        }
    }

    fun setRectangleInfo(inputFactory: InputFactory): Rectangle {
        putId(inputFactory.randomId)
        val id = rectangleId.getId()
        val point = RectanglePoint(inputFactory.pointX, inputFactory.pointY)
        val size = RectangleSize()
        val color = RectangleColor(inputFactory.colorR,inputFactory.colorG,inputFactory.colorB)
        val alpha = RectangleAlpha(inputFactory.alpha).alpha
        return Rectangle(inputFactory.count , id, point, size, color, alpha)
    }

    fun getRectangleInfo(rectangle: Rectangle) = rectangle.toString()

}