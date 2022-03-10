package com.example.drawingapp.draw

import android.graphics.Bitmap
import android.graphics.Rect
import com.example.drawingapp.Contract
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.data.Repository
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle

class RectanglePresenter(
    private val view: Contract.View,
    private val repository: Repository
) : Contract.Presenter {

    override fun plane() = repository.plane

    override fun onClickLog() = view.getDrawMessage(getRectangleLog())

    override fun getRectangleLog() = repository.getRectangleLog(getRectangle())

    override fun getInput(inputType: InputType) = repository.getInputFactory(inputType)

    override fun setPlaneXY(typeList: List<Type>) {
        repository.setPlaneXY(typeList)
    }

    override fun getRectangle() = repository.getRectangle(getInput(InputType.RECTANGLE))

    override fun getPicture(bitmap: Bitmap) =
        repository.getPicture(getInput(InputType.PICTURE), bitmap)

    override fun setPlane() = repository.setPlane(getRectangle())

    override fun setPlane(bitmap: Bitmap) = repository.setPlane(getPicture(bitmap))

    override fun setAlpha(index: Int, value: Int) {
        repository.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = repository.getAlpha(index)

    override fun drawPicture() {
        val picture = repository.getPlane(
            repository.getPlaneCount()?.minus(1) ?: throw IllegalArgumentException("stub!")
        ) as Picture
        view.drawPicture(picture)
    }

    override fun drawRectangle() {
        val rectangle = repository.getPlane(
            repository.getPlaneCount()?.minus(1) ?: throw IllegalArgumentException("stub!")
        ) as Rectangle
        view.drawRectangle(rectangle)
    }

    override fun resetClick() {
        repository.resetClick()
    }

    override fun setClick(index: Int) {
        repository.setClick(index)
    }

}