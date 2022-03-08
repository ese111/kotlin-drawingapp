package com.example.drawingapp.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.drawingapp.data.Rectangle
import com.example.drawingapp.data.RectangleColor
import com.example.drawingapp.data.RectanglePoint
import com.example.drawingapp.data.RectangleSize

class RectangleDraw : View {

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

    private var getClickRectangle = -1

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            rectangleCanvas = canvas
        }

        rectangleCanvas.drawColor(Color.WHITE)
        if (rect.size != 0) {
            var index = 0
            rect.forEach { rect ->
                paints[index]?.let { paint -> rectangleCanvas.drawRect(rect, paint) }
                index++
            }
            strokeRect.forEach {
                rectangleCanvas.drawRect(it, stroke)
            }
        }
    }

    fun getClickRectangle() = getClickRectangle

    private fun initStroke() {
        stroke = Paint()
        stroke.color = Color.BLUE
        stroke.strokeWidth = 4F
        stroke.style = Paint.Style.STROKE
    }

    fun setPaints(paint: Paint) {
        paints.add(paint)
    }

    fun setRects(_rect: Rect) {
        rect.add(_rect)
    }

    fun findRectangle(pointF: PointF): Int {
        var count = 0
        rect.forEach {
            if (it.checkContains(pointF.x.toInt(), pointF.y.toInt())) {
                strokeRect.add(it)
                getClickRectangle = count
                return count
            }
            count++
        }
        getClickRectangle = -1
        return getClickRectangle
    }

    fun strokeRectReset() = strokeRect.clear()

    private fun Rect.checkContains(x: Int, y: Int) =
        this.right >= x && this.left <= x && this.top >= y && this.bottom <= y

    fun changeAlpha(rectangle: Rectangle, index: Int) {
        paints[index].alpha = rectangle.getAlpha() * 25
    }


}