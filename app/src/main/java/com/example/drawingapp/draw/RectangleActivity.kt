package com.example.drawingapp.draw

import android.graphics.Canvas
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.drawingapp.Contract
import com.example.drawingapp.R
import com.example.drawingapp.data.Rectangle
import com.example.drawingapp.data.RectangleRepository
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class RectangleActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Contract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())
        presenter = RectanglePresenter(this, RectangleRepository())
//        presenter.onClickLog()
//        presenter.onClickLog()
//        presenter.onClickLog()
//        presenter.onClickLog()
        val drawButton: Button = findViewById(R.id.btn_make_rectangle)
        drawButton.text = "사각형"
        drawButton.setOnClickListener() {
//            presenter.onClickLog()
            presenter.setPlane()
            presenter.onClickDraw()
        }

    }

    override fun getDrawMessage(message: String) {
        Logger.i(message)
    }

    override fun drawRectangle(rectangle: Rectangle) {
        val draw: RectangleDraw = findViewById(R.id.draw_rectangle)
        draw.drawRectangle(rectangle)
        Logger.d(rectangle.toString())
    }

    override fun setTouchRectangle(rectangle: Rectangle) {
        TODO("Not yet implemented")
    }

}