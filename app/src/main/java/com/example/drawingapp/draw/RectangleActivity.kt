package com.example.drawingapp.draw

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.drawingapp.Contract
import com.example.drawingapp.R
import com.example.drawingapp.data.*
import com.example.drawingapp.util.showSnackBar
import com.google.android.material.slider.Slider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class RectangleActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Contract.Presenter
    private lateinit var draw: RectangleDraw
    private lateinit var slider: Slider
    private var choiceRect = -1
    private val rect = ListLiveData<Rect>()
    private val paints = ListLiveData<Paint>()
    private val rectangleColor = mutableListOf<String>()

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
                if (slider.value < 10F) {
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
        rect.observe(this) {
            draw.invalidate()
        }

        if (choiceRect != -1) {
            paints.observe(this) { draw.invalidate() }
        }
    }

    override fun changeAlpha(rectangle: Rectangle, index: Int) {
        paints.getList()?.get(index)?.alpha = rectangle.getAlpha() * 25
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
            color.text = "#${rectangleColor[count]}"
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
        setRect(rectangle)
        setPaints(rectangle)
        setColorList(rectangle.rectangleColor)
    }


    private fun setColorList(_rectangleColor: RectangleColor) =
        rectangleColor.add(setColor(_rectangleColor))

    private fun setPaints(rectangle: Rectangle) {
        val paint = Paint()

        paint.color = Color.argb(
            rectangle.getAlpha() * 25,
            rectangle.rectangleColor.red,
            rectangle.rectangleColor.green,
            rectangle.rectangleColor.blue
        )

        paint.style = Paint.Style.FILL
        paints.add(paint)
        draw.setPaints(paint)
    }

    private fun setRect(rectangle: Rectangle) {
        val point = getPoints(rectangle.rectanglePoint, rectangle.rectangleSize)
        rect.add(Rect(point[0], point[1], point[2], point[3]))
        draw.setRects(Rect(point[0], point[1], point[2], point[3]))
    }

    private fun getPoints(
        point: RectanglePoint,
        size: RectangleSize
    ): IntArray {
        val start = getStart(point.x, size.width)
        val end = getEnd(point.x, size.width)
        val top = getTop(point.y, size.height)
        val bottom = getBottom(point.y, size.height)
        return intArrayOf(start, top, end, bottom)
    }

    private fun getStart(
        x: Int,
        width: Int
    ) = x - (width / 2)

    private fun getEnd(
        x: Int,
        width: Int
    ) = x + (width / 2)

    private fun getTop(
        y: Int,
        height: Int
    ) = y + (height / 2)

    private fun getBottom(
        y: Int,
        height: Int
    ) = y - (height / 2)

    private fun setColor(rectangleColor: RectangleColor) = rectangleColor.red.toString(16) +
            rectangleColor.blue.toString(16) +
            rectangleColor.green.toString(16)

}
