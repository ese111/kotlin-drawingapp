package com.example.drawingapp.draw

import com.example.drawingapp.Contract
import com.example.drawingapp.data.Rectangle

class RectanglePresenter(
    private val view: Contract.View,
    private val repository: Contract.Repository
) : Contract.Presenter {

    override fun getRectangleLog() = repository.getRectangleLog(getRectangle())

    override fun getInput() = repository.getInputFactory()

    override fun onClickLog() = view.getDrawMessage(getRectangleLog())

    override fun getRectangle() = repository.getRectangle(getInput())

    override fun setPlane() = repository.setPlane(getRectangle())

    override fun onClickDraw() {
        for (i in 0 until repository.getPlaneCount()) {
            val rectangle = repository.getPlane(i)
            view.drawRectangle(rectangle)
        }
    }

}