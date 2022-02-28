package com.example.drawingapp.draw

import com.example.drawingapp.BasePresenter
import com.example.drawingapp.BaseView

interface RectangleContract {
    interface View : BaseView<Presenter> {

    }
    interface Presenter : BasePresenter {

    }
}