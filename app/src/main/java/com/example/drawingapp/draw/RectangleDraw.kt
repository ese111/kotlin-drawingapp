package com.example.drawingapp.draw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.RequiresPermission
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.drawingapp.Draw
import com.example.drawingapp.data.*
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class RectangleDraw : Draw, View {

    constructor(context: Context?) : super(context) {
        initStroke()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initStroke()
    }

    private lateinit var rectangleCanvas: Canvas

    private lateinit var stroke: Paint

    private val rect = mutableListOf<Rect>()

    private val strokeRect = mutableListOf<Rect>()

    private val paints = mutableListOf<Paint>()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            rectangleCanvas = canvas
        }

        rectangleCanvas.drawColor(Color.WHITE)

        if (rect.size != 0) {
            var index = 0
            rect.forEach {
                rectangleCanvas.drawRect(it, paints[index])
                index++
            }
            strokeRect.forEach{
                rectangleCanvas.drawRect(it, stroke)
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val pointF = PointF(event!!.x, event.y)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                checkContains(pointF)
                Logger.d("터치했다.")
            }
            MotionEvent.ACTION_MOVE -> {
                checkContains(pointF)
                Logger.d("터치하는중이다.")
            }
            else -> {
                performClick()
            }
        }
        invalidate()
        return true
    }

    private fun initStroke(){
        stroke = Paint()
        stroke.color = Color.BLUE
        stroke.strokeWidth = 40F
        stroke.style = Paint.Style.STROKE
    }

    private fun checkContains(pointF: PointF) {
        rect.forEach{
            Logger.d("${pointF.x.toInt()}, ${pointF.y.toInt()}")
            Logger.d("${it.right}, ${it.left}, ${it.top}, ${it.bottom}")
            if (it.checkRange(pointF.x.toInt(), pointF.y.toInt())) {
                Logger.d("사각형 검사")
                strokeRect.add(it)
            }
        }
    }
    private fun Rect.checkRange(x: Int, y: Int) =
        this.right >= x && this.left <= x && this.top >= y && this.bottom <= y

    override fun drawRectangle(rectangle: Rectangle) {

        val paint = Paint()

        val color = setColor(rectangle.rectangleColor).toInt(16)

        paint.color = Color.rgb(
            rectangle.rectangleColor.red,
            rectangle.rectangleColor.green,
            rectangle.rectangleColor.blue
        )
        paint.style = Paint.Style.FILL
        paints.add(paint)

        val point = getPoints(rectangle.rectanglePoint, rectangle.rectangleSize)

        rect.add(Rect(point[0], point[1], point[2], point[3]))

        invalidate()
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