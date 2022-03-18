package com.example.drawingapp.data

import android.graphics.Bitmap
import com.example.drawingapp.data.attribute.Id
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.orhanobut.logger.Logger

class RectangleRepository : Repository {
    private var count = 1
    private val id: Id = Id()
    override val plane = Plane()

    override fun setPlaneXY(typeList: List<Type>) {
        plane.setXY(typeList)
    }

    override fun resetClick() {
        plane.resetClick()
    }

    override fun setClick(index: Int) {
        plane.list.getList()?.get(index)?.click = true
    }

    private fun makeRectangle(): Rectangle {
        val rect = Rectangle.make(count, id)
        count++
        return rect
    }

    private fun makeText(): Text {
        val text = Text.make(count, id)
        count++
        return text
    }

    private fun makePicture(bitmap: Bitmap): Picture {
        val pic = Picture.make(count, id, bitmap)
        count++
        return pic
    }

    override fun getLastPlane() = plane.list.value?.last()

    override fun setRectangleInPlane() {
        val rect = makeRectangle()
        plane.setPlane(rect)
    }

    override fun setPictureInPlane(bitmap: Bitmap) {
        val pic = makePicture(bitmap)
        plane.setPlane(pic)
    }

    override fun setTextInPlane() {
        val pic = makeText()
        plane.setPlane(pic)
    }

    override fun getPlane(index: Int): Type? = plane.getPlane(index)

    override fun getPlaneCount(): Int? = plane.getCount()

    override fun setAlpha(index: Int, value: Int) {
        plane.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = getPlane(index)?.alpha

}

