package com.example.drawingapp.draw

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.drawingapp.Contract
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.data.Repository
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.example.drawingapp.data.attribute.Size

class RectanglePresenter(
    private val view: Contract.View,
    private val repository: Repository
) : Contract.Presenter {

    override fun plane() = repository.plane

    override fun onClickLog() = view.drawMessage(getLastRectangle().toString())

    private fun getLastRectangle() = repository.getLastPlane()

    override fun setPlaneXY(typeList: List<Type>) {
        repository.setPlaneXY(typeList)
    }

    override fun setRectangleInPlane() = repository.setRectangleInPlane()

    override fun setPictureInPlane(bitmap: Bitmap) = repository.setPictureInPlane(bitmap)

    override fun setAlpha(index: Int, value: Int) {
        repository.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = repository.getAlpha(index)

    override fun drawPicture() {
        val picture = repository.getLastPlane() as Picture
        view.drawPicture(picture)
    }

    override fun drawRectangle() {
        val rectangle = repository.getLastPlane() as Rectangle
        view.drawRectangle(rectangle)
    }

    override fun resetClick() {
        repository.resetClick()
    }

    override fun setClick(index: Int) {
        repository.setClick(index)
    }


}