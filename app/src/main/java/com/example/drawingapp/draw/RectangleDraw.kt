package com.example.drawingapp.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.orhanobut.logger.Logger

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

//    private val rect = mutableListOf<Rect>()
//
//    private val pic = mutableListOf<Picture>()

    private val drawType = mutableListOf<Type>()

    private val strokeRect = mutableSetOf<Rect>()

    private var getClickRectangle = -1

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            rectangleCanvas = canvas
        }

        rectangleCanvas.drawColor(Color.WHITE)

        if (drawType.isNotEmpty()) {
            var index = 0
            drawType.forEach { type ->
                when(type.type == "RECTANGLE") {
                    true -> {
                        paints[index]?.let { paint -> rectangleCanvas.drawRect(type.rect, paint) }
                        index++
                    }

                    false -> {
                        type as Picture
                        paints[index]?.let { paint -> rectangleCanvas.drawBitmap(type.bitmap, type.point.x, type.point.y, paint) }
                    }
                }

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

    fun setDrawType(type: Type) {
        drawType.add(type)
    }

    fun findRectangle(pointF: PointF): Int {
        var count = 0
        drawType.forEach {
            Logger.i("1 left: ${it.rect.left} top: ${it.rect.top}  right: ${it.rect.right}  bottom:  ${it.rect.bottom} x: ${pointF.x} y: ${pointF.y} count: ${count}")
            if (it.rect.checkContains(pointF.x.toInt(), pointF.y.toInt())) {
                strokeRect.add(it.rect)
                getClickRectangle = count
                Logger.i("2 left: ${it.rect.left} top: ${it.rect.top}  right: ${it.rect.right}  bottom:  ${it.rect.bottom} x: ${pointF.x} y: ${pointF.y} count: ${count}")
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

    fun changeAlpha(type: Type, index: Int) {
        paints[index].alpha = type.getAlpha() * 25
        invalidate()
    }

}