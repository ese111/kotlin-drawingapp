package com.example.drawingapp.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.input.InputType
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

    private val drawType = mutableListOf<Type>()

    private val isClick = mutableListOf<Boolean>()

    private var getClickRectangle = -1

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            rectangleCanvas = canvas
        }

        rectangleCanvas.drawColor(Color.WHITE)

        if (drawType.isNotEmpty()) {
            for (i in 0 until drawType.size) {
                when (drawType[i].type == InputType.RECTANGLE) {
                    true -> {
                        paints[i]?.let { paint ->
                            rectangleCanvas.drawRect(
                                drawType[i].rect,
                                paint
                            )
                        }
                    }

                    false -> {
                        val pic = drawType[i] as Picture
                        paints[i]?.let { paint ->
                            rectangleCanvas.drawBitmap(
                                pic.bitmap,
                                pic.point.x.toFloat(),
                                pic.point.y.toFloat(),
                                paint
                            )
                        }
                    }
                }
                if (isClick[i]) {
                    rectangleCanvas.drawRect(drawType[i].rect, stroke)
                }
            }
        }
    }

    fun getClickRectangle() = getClickRectangle

    fun setXY(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            when (it.type) {
                InputType.RECTANGLE -> {
                    setTypeList.add(setRectXY(index, x, y))
                }
                InputType.PICTURE -> {
                    setTypeList.add(setPicXY(index, x, y))
                }
            }
            index++
        }
        return setTypeList
    }

    private fun setRectXY(index: Int, x: Int, y: Int): Type {
        if (isClick[index]) {
            val resultX = (drawType[index].rect.left + (drawType[index].size.width / 2)) + x
            val resultY = (drawType[index].rect.top - (drawType[index].size.height / 2)) + y
            drawType[index].rect.left = resultX - (drawType[index].size.width / 2)
            drawType[index].rect.top = resultY + (drawType[index].size.height / 2)
            drawType[index].rect.right = resultX + (drawType[index].size.width / 2)
            drawType[index].rect.bottom = resultY - (drawType[index].size.height / 2)
        }
        return drawType[index]
    }

    private fun setPicXY(index: Int, x: Int, y: Int): Type {
        if (isClick[index]) {
            drawType[index].point.x += x
            drawType[index].point.y += y
            drawType[index].rect.left = drawType[index].point.x
            drawType[index].rect.top = drawType[index].point.y + 120
            drawType[index].rect.right = drawType[index].point.x + 150
            drawType[index].rect.bottom = drawType[index].point.y
        }
        return drawType[index]
    }

    fun setStrokeClean() {
        for (i in 0 until isClick.size) {
            isClick[i] = false
        }
    }

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
        isClick.add(false)
    }

    fun findRectangle(pointF: PointF): Int {
        var count = 0
        drawType.forEach {
            if (it.rect.checkContains(pointF.x.toInt(), pointF.y.toInt())) {
                isClick[count] = true
                getClickRectangle = count
                Logger.wtf("원 ${pointF.x}, ${pointF.y}: ${it.rect.left}, ${it.rect.top}, ${it.rect.right}, ${it.rect.bottom}")
                return count
            }
            count++
        }
        getClickRectangle = -1
        return getClickRectangle
    }

    private fun Rect.checkContains(x: Int, y: Int) =
        this.right >= x && this.left <= x && this.top >= y && this.bottom <= y

    fun changeAlpha(index: Int, alpha: Int) {
        paints[index].alpha = alpha * 25
        invalidate()
    }

}