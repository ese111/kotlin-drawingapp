package com.example.drawingapp.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.RequiresPermission
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.drawingapp.Draw
import com.example.drawingapp.data.*
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class RectangleDraw : Draw, View {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private lateinit var rectangleCanvas: Canvas

    private val points = mutableListOf<FloatArray>()

    private val paints = mutableListOf<Paint>()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            rectangleCanvas = canvas
        }

        rectangleCanvas.drawColor(Color.WHITE)

        if(points.size != 0) {
            var index = 0
            points.forEach{
                rectangleCanvas.drawRect( it[0], it[1], it[2], it[3], paints[index])
                index++
            }
        }

    }

    override fun drawRectangle(rectangle: Rectangle) {

        val paint = Paint()

        val color = setColor(rectangle.rectangleColor).toInt(16)

        paint.color = Color.rgb(
            rectangle.rectangleColor.red,
            rectangle.rectangleColor.green,
            rectangle.rectangleColor.blue
        )

        paints.add(paint)

        points.add(getPoints(rectangle.rectanglePoint, rectangle.rectangleSize))

        invalidate()
    }

    private fun getPoints(
        point: RectanglePoint,
        size: RectangleSize
    ): FloatArray {
        val start = getStart(point.x, size.width).toFloat()
        val end = getEnd(point.x, size.width).toFloat()
        val top = getTop(point.y, size.height).toFloat()
        val bottom = getBottom(point.y, size.height).toFloat()
        return floatArrayOf(start, top, end, bottom)
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