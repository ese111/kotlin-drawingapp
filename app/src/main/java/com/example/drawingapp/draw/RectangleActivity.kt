package com.example.drawingapp.draw

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drawingapp.Contract
import com.example.drawingapp.R
import com.example.drawingapp.data.Rectangle
import com.example.drawingapp.data.RectangleRepository
import com.example.drawingapp.util.showSnackBar
import com.google.android.material.slider.Slider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class RectangleActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Contract.Presenter
    private lateinit var draw: RectangleDraw
    private lateinit var slider: Slider
    private var choiceRect = -1

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
            presenter.getDrawRectangle()
        }

        slider = findViewById(R.id.slider_invisible)

        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {

            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                if(slider.value < 10F){
                    slider.value = 10F
                }
                if (choiceRect == -1) {
                    findViewById<ConstraintLayout>(R.id.container).showSnackBar("선택된 사각형이 없습니다.")
                    slider.value = 1F
                    return
                }
                var alpha = slider.value / 10

                setAlpha(choiceRect, alpha.toInt())
                presenter.getDrawRectangle(choiceRect)
            }
        })
    }

    override fun changeAlpha(rectangle: Rectangle, index: Int) {
        draw.changeAlpha(rectangle, index)
    }

    override fun setAlpha(index: Int, value: Int) {
        presenter.setAlpha(index, value)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val count = draw.getClickRectangle()
        setColorText(count)
        val pointF = PointF(event!!.x, event!!.y - 176F)
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                onTouchRectangle(pointF)
            }
            MotionEvent.ACTION_MOVE -> {
                onTouchRectangle(pointF)
            }
            else -> {
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
            return
        }
        choiceRect = draw.getClickRectangle()
        slider.value = presenter.getAlpha(choiceRect) * 10F
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
        Logger.wtf(rectangle.toString())
    }

}
