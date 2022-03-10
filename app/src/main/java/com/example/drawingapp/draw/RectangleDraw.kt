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
                when(drawType[i].type == InputType.RECTANGLE) {
                    true -> {
                        paints[i]?.let { paint -> rectangleCanvas.drawRect(drawType[i].rect, paint) }
                    }

                    false -> {
                        val pic = drawType[i] as Picture
                        paints[i]?.let { paint -> rectangleCanvas.drawBitmap(pic.bitmap, pic.point.x, pic.point.y, paint) }
                    }
                }
                if(isClick[i]) {
                    rectangleCanvas.drawRect(drawType[i].rect, stroke)
                }
            }
        }
    }

    fun getClickRectangle() = getClickRectangle

    fun setRect(index: Int, _rect: Rect) = when(drawType[index].type) {
        InputType.RECTANGLE -> {
            drawType[index].rect.left = _rect.left
            drawType[index].rect.top = _rect.top
            drawType[index].rect.right = _rect.right
            drawType[index].rect.bottom = _rect.bottom
            invalidate()
        }
        InputType.PICTURE -> {
            val pic = drawType[index] as Picture
            pic.point.x = _rect.left.toFloat()
            pic.point.y = _rect.bottom.toFloat()
            invalidate()
        }
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
            Logger.i("${it.rect.right}, ${it.rect.left}, ${it.rect.top}, ${it.rect.bottom}, ${pointF.x}, ${pointF.y}")
            Logger.wtf("${it.rect.checkContains(pointF.x.toInt(), pointF.y.toInt())}")

            if (it.rect.checkContains(pointF.x.toInt(), pointF.y.toInt())) {
                isClick[count] = true
                getClickRectangle = count
                return count
            }
            count++
        }
        getClickRectangle = -1
        return getClickRectangle
    }

    private fun Rect.checkContains(x: Int, y: Int) =
        this.right >= x && this.left <= x && this.top >= y && this.bottom <= y

    fun changeAlpha(type: Type, index: Int) {
        paints[index].alpha = type.getAlpha() * 25
        invalidate()
    }

}