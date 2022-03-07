package com.example.drawingapp.draw

import com.example.drawingapp.Contract
import com.example.drawingapp.data.Repository

class RectanglePresenter(
    private val view: Contract.View,
    private val repository: Repository
) : Contract.Presenter {

    override fun getRectangleLog() = repository.getRectangleLog(getRectangle())

    override fun getInput() = repository.getInputFactory()

    override fun onClickLog() = view.getDrawMessage(getRectangleLog())

    override fun getRectangle() = repository.getRectangle(getInput())

    override fun setPlane() = repository.setPlane(getRectangle())

    override fun setAlpha(index: Int, value: Int) {
        repository.setAlpha(index, value)
    }

    override fun getAlpha(index: Int) = repository.getAlpha(index)

    override fun getDrawRectangle(index: Int) {
        val rectangle = repository.getPlane(index)
        view.changeAlpha(rectangle, index)
    }

    override fun getDrawRectangle() {
        val rectangle = repository.getPlane(repository.getPlaneCount()-1)
        view.drawRectangle(rectangle)
    }
}