package com.example.drawingapp.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.drawingapp.Contract
import com.example.drawingapp.Draw
import com.example.drawingapp.data.Rectangle
import com.example.drawingapp.data.RectangleColor
import com.example.drawingapp.data.RectanglePoint
import com.example.drawingapp.data.RectangleSize
import java.util.logging.Logger

class RectangleDraw : Draw, View {

    constructor(context: Context?) : super(context) {
        initStroke()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initStroke()
    }

    private lateinit var rectangleCanvas: Canvas

    private lateinit var stroke: Paint

    private val paints = mutableListOf<Paint>()

    private val rect = mutableListOf<Rect>()

    private val strokeRect = mutableSetOf<Rect>()

    private var rectangleColor = mutableListOf<String>()

    private var onClickRectangle = -1

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
            strokeRect.forEach {
                rectangleCanvas.drawRect(it, stroke)
            }
        }

    }

    override fun onClickRectangleIndex() = onClickRectangle

    private fun initStroke() {
        stroke = Paint()
        stroke.color = Color.BLUE
        stroke.strokeWidth = 4F
        stroke.style = Paint.Style.STROKE
    }

    override fun findRectangle(pointF: PointF): Int {
        var count = 0
        rect.forEach {
            if (it.checkContains(pointF.x.toInt(), pointF.y.toInt())) {
                strokeRect.add(it)
                return count
            }
            count++
        }
        return -1
    }

    override fun strokeRectReset() = strokeRect.clear()

    private fun Rect.checkContains(x: Int, y: Int) =
        this.right >= x && this.left <= x && this.top >= y && this.bottom <= y

    override fun drawRectangle(rectangle: Rectangle) {

        val paint = Paint()

        rectangleColor.add(setColor(rectangle.rectangleColor))

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

    override fun getColor(index: Int) = rectangleColor[index]

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