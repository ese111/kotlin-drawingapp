package com.example.drawingapp.draw

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.drawingapp.Contract
import com.example.drawingapp.R
import com.example.drawingapp.data.Rectangle
import com.example.drawingapp.data.RectangleRepository
import com.google.android.material.slider.Slider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class RectangleActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Contract.Presenter
    private lateinit var draw: RectangleDraw

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())
        draw = findViewById(R.id.draw_rectangle)
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
            Logger.i("plane data change")
            presenter.getPlaneData()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val count = draw.onClickRectangleIndex()
        setColorText(count)
        val pointF = PointF(event!!.x, event!!.y - 176F)
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                Logger.d("main ${event.x}, ${event.y}")
                onTouchRectangle(pointF)
            }
            MotionEvent.ACTION_MOVE -> {
                Logger.d("main ${event.x}, ${event.y}")
                onTouchRectangle(pointF)
            }
            else -> {
                Logger.d("main ${event.x}, ${event.y}")
                draw.performClick()
            }
        }
        draw.invalidate()
        return true
    }

    override fun onTouchRectangle(pointF: PointF) {
        val count = draw.findRectangle(pointF)
        if (count == -1) {
            draw.strokeRectReset()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setColorText(count: Int) = when (count != -1) {
        true -> {
            val color: Button = findViewById(R.id.tv_background_color)
            color.text = "#${draw.getColor(count)}"
        }

        false -> {
            val color: Button = findViewById(R.id.tv_background_color)
            color.text = ""
        }
    }

    override fun getDrawMessage(message: String) {
        Logger.i(message)
    }

    override fun drawRectangle(rectangle: Rectangle) {
        draw.drawRectangle(rectangle)
        Logger.d(rectangle.toString())
    }

}