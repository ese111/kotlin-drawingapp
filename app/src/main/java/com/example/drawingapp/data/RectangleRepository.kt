package com.example.drawingapp.data

import com.example.drawingapp.InputFactory

class RectangleRepository : Repository {
    private var count = 1
    private val rectangleId: RectangleId = RectangleId()
    private val plane = Plane()

    private fun putId(randomId: String) {
        while (true) {
            val id = rectangleId.makeRandomId(randomId)
            if (rectangleId.checkId(id)) {
                rectangleId.putId(id)
                count++
                return
            }
        }
    }

    override fun getInputFactory() = RectangleInput(count)

    override fun getRectangle(inputFactory: InputFactory): Rectangle {
        putId(inputFactory.randomId)
        val id = rectangleId.getId()
        val point = RectanglePoint(inputFactory.pointX, inputFactory.pointY)
        val size = RectangleSize()
        val color = RectangleColor(inputFactory.colorR, inputFactory.colorG, inputFactory.colorB)
        val alpha = inputFactory.alpha
        return Rectangle(inputFactory.count, id, point, size, color, alpha)
    }

    override fun getRectangleLog(rectangle: Rectangle) = rectangle.toString()

    override fun setPlane(rectangle: Rectangle) = plane.setPlane(rectangle)

    override fun getPlane(index: Int) = plane.getPlane(index)

    override fun getPlaneCount() = plane.getCount()

    override fun setAlpha(index: Int, value: Int) {
        plane.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = getPlane(index).getAlpha()

}