package com.example.drawingapp.data

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import com.example.drawingapp.InputFactory
import com.example.drawingapp.data.attribute.*
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.data.input.PictureInput
import com.example.drawingapp.data.input.RectangleInput

class RectangleRepository : Repository {
    private var count = 1
    private val id: Id = Id()
    override val plane = Plane()

    private fun putId(randomId: String) {
        while (true) {
            val id = id.makeRandomId(randomId)
            if (this.id.checkId(id)) {
                this.id.putId(id)
                count++
                return
            }
        }
    }

    override fun getInputFactory(type: InputType) = when (type) {
        InputType.RECTANGLE -> RectangleInput(count)
        InputType.PICTURE -> PictureInput(count)
    }

    override fun setPlaneXY(index: Int, rect: Rect?) {
        plane.setXY(index, rect)
    }

    override fun getRectangle(inputFactory: InputFactory): Rectangle {
        inputFactory as RectangleInput
        putId(inputFactory.randomId)
        val id = id.getId()
        val point = Point(inputFactory.pointX, inputFactory.pointY)
        val size = Size()
        val color = Color(inputFactory.colorR, inputFactory.colorG, inputFactory.colorB)
        val alpha = inputFactory.alpha
        val rect = inputFactory.getRect()
        return Rectangle(inputFactory.count, id, point, size, color, alpha, rect)
    }

    override fun getPicture(inputFactory: InputFactory, bitmap: Bitmap): Picture {
        inputFactory as PictureInput
        putId(inputFactory.randomId)
        val id = id.getId()
        val point = PointF(inputFactory.pointX.toFloat(), inputFactory.pointY.toFloat())
        val rect = inputFactory.getRect()
        val alpha = inputFactory.alpha
        return Picture(inputFactory.count, id, bitmap, point, alpha, rect)
    }

    override fun getRectangleLog(type: Type) = type.toString()

    override fun setPlane(type: Type) = plane.setPlane(type)

    override fun getPlane(index: Int): Type? = plane.getPlane(index)

    override fun getPlaneCount(): Int? = plane.getCount()

    override fun setAlpha(index: Int, value: Int) {
        plane.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = getPlane(index)?.getAlpha()
}

