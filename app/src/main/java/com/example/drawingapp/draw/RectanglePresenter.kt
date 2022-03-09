package com.example.drawingapp.draw

import android.graphics.Bitmap
import com.example.drawingapp.Contract
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.data.Repository
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle

class RectanglePresenter(
    private val view: Contract.View,
    private val repository: Repository
) : Contract.Presenter {

    override fun onClickLog() = view.getDrawMessage(getRectangleLog())

    override fun getRectangleLog() = repository.getRectangleLog(getRectangle())

    override fun getInput(inputType: InputType) = repository.getInputFactory(inputType)

    override fun getRectangle() = repository.getRectangle(getInput(InputType.RECTANGLE))

    override fun getPicture(bitmap: Bitmap) = repository.getPicture(getInput(InputType.PICTURE),bitmap)

    override fun setPlane() = repository.setPlane(getRectangle())

    override fun setPlane(bitmap: Bitmap) = repository.setPlane(getPicture(bitmap))

    override fun setAlpha(index: Int, value: Int) {
        repository.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = repository.getAlpha(index)

    override fun getDrawPicture() {
        val picture = repository.getPlane(repository.getPlaneCount() - 1) as Picture
        view.drawPicture(picture)
    }

    override fun getDrawPicture(index: Int) {
        val picture = repository.getPlane(index)
        view.changeAlpha(picture, index)
    }

    override fun getDrawRectangle() {
        val rectangle = repository.getPlane(repository.getPlaneCount() - 1) as Rectangle
        view.drawRectangle(rectangle)
    }

    override fun getDrawRectangle(index: Int) {
        val rectangle = repository.getPlane(index)
        view.changeAlpha(rectangle, index)
    }

}