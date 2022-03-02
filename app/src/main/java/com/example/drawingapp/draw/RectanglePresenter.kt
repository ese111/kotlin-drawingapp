package com.example.drawingapp.draw

import com.example.drawingapp.Contract

class RectanglePresenter(
    private val view: Contract.View,
    private val repository: Contract.Repository) : Contract.Presenter {

    override fun getDrawRectangle(): String {
        val inputFactory = repository.getInputFactory()
        return repository.getRectangleInfo(repository.setRectangleInfo(inputFactory))
    }

    override fun onClickLog() = view.getDrawMessage(getDrawRectangle())

}