package com.example.drawingapp.draw

import android.graphics.Bitmap
import com.example.drawingapp.Contract
import com.example.drawingapp.data.Plane
import com.example.drawingapp.data.Repository
import com.example.drawingapp.data.Text
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle

class RectanglePresenter(
    private val view: Contract.View,
    private val repository: Repository
) : Contract.Presenter {

    override fun plane() = repository.plane

    override fun onClickLog() = view.drawMessage(getLastRectangle().toString())

    override fun getLastRectangle() = repository.getLastPlane()

    override fun setPlaneXY(typeList: List<Type>) {
        repository.setPlaneXY(typeList)
    }

    override fun setRectangleInPlane() = repository.setRectangleInPlane()

    override fun setTextInPlane() = repository.setTextInPlane()

    override fun setPictureInPlane(bitmap: Bitmap) = repository.setPictureInPlane(bitmap)

    override fun setAlpha(index: Int, value: Int) {
        repository.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = repository.getAlpha(index)

    override fun drawPicture(drawPicture: (Picture) -> Unit) {
        val picture = repository.getLastPlane() as Picture
        drawPicture(picture)
    }

    override fun drawRectangle(draw: (Rectangle) -> Unit) {
        val rectangle = repository.getLastPlane() as Rectangle
        draw(rectangle)
    }

    override fun drawText(draw: (Text) -> Unit) {
        val text = repository.getLastPlane() as Text
        draw(text)
    }


    override fun resetClick() {
        repository.resetClick()
    }

    override fun setClick(index: Int) {
        repository.setClick(index)
    }


}