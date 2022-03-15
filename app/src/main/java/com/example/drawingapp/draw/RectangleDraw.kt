package com.example.drawingapp.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.example.drawingapp.Contract
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.example.drawingapp.data.input.InputType

class RectangleDraw : View {

    constructor(context: Context?) : super(context) {
        initStroke()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initStroke()
    }

    private lateinit var mCanvas: Canvas

    private lateinit var stroke: Paint

    private val paints = mutableListOf<Paint>()

    private val drawType = mutableListOf<Type>()

    private var getClickRectangle = -1

    private val tempSet = mutableSetOf<Type>()

    lateinit var mPresenter: Contract.Presenter

    fun initPresenter(presenter: Contract.Presenter) {
        mPresenter = presenter
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            mCanvas = canvas
        }

        mCanvas.drawColor(Color.WHITE)

        if (drawType.isNotEmpty()) {
            startDraw()
        }

        if (tempSet.isNotEmpty()) {
            move()
        }

    }

    private fun move() {
        tempSet.forEach {
            drawMovePicAndRect(it)
        }
    }

    private fun drawMovePicAndRect(type: Type) {
        when (type.type == InputType.RECTANGLE) {
            true -> setTempRect(type)
            false -> setTempPic(type)
        }

        val tempStroke = Paint().apply {
            color = Color.BLUE
            strokeWidth = 4F
            style = Paint.Style.STROKE
            alpha = 5
        }

        mCanvas.drawRect(type.rect, tempStroke)
    }

    private fun setTempPic(type: Type) {
        val pic = type as Picture
        val paint = Paint().apply {
            alpha = pic.alpha * 25 / 2
            style = Paint.Style.FILL
        }

        mCanvas.drawBitmap(
            pic.bitmap,
            pic.point.x.toFloat(),
            pic.point.y.toFloat(),
            paint
        )
    }

    private fun setTempRect(type: Type) {
        val rect = type as Rectangle
        val paint = Paint().apply {
            color = Color.argb(
                rect.alpha * 25 / 2,
                rect.color.red,
                rect.color.green,
                rect.color.blue
            )
            style = Paint.Style.FILL
        }

        mCanvas.drawRect(
            rect.rect,
            paint
        )
    }

    private fun startDraw() {
        for (i in 0 until drawType.size) {
            drawFactory(i)
        }
    }

    private fun drawFactory(index: Int) {
        when (drawType[index].type == InputType.RECTANGLE) {
            true -> drawRect(index)
            false -> drawPic(index)
        }

        if (drawType[index].click) {
            mCanvas.drawRect(drawType[index].rect, stroke)
        }
    }

    private fun drawRect(index: Int) {
        paints[index]?.let { paint ->
            mCanvas.drawRect(
                drawType[index].rect,
                paint
            )
        }
    }

    private fun drawPic(index: Int) {
        val pic = drawType[index] as Picture
        paints[index]?.let { paint ->
            mCanvas.drawBitmap(
                pic.bitmap,
                pic.point.x.toFloat(),
                pic.point.y.toFloat(),
                paint
            )
        }
    }

    fun getClickRectangle() = getClickRectangle

    fun setXY(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            when (it.type) {
                InputType.RECTANGLE -> {
                    setTypeList.add(setRectXY(it, x, y))
                }
                InputType.PICTURE -> {
                    setTypeList.add(setPicXY(it, x, y))
                }
            }
            index++
        }
        return setTypeList
    }

    private fun setRectXY(rect: Type, x: Int, y: Int): Type {
        rect.takeIf { it.click }?.apply {
            val resultX = (this.rect.left + (this.size.width / 2)) + x
            val resultY = (this.rect.top - (this.size.height / 2)) + y
            this.rect.left = resultX - (this.size.width / 2)
            this.rect.top = resultY + (this.size.height / 2)
            this.rect.right = resultX + (this.size.width / 2)
            this.rect.bottom = resultY - (this.size.height / 2)
        }
        return rect.copy()
    }

    private fun setPicXY(pic: Type, x: Int, y: Int): Type {
        pic.takeIf { it.click }?.apply {
            this.point.x += x
            this.point.y += y
            this.rect.left = this.point.x
            this.rect.top = this.point.y + 120
            this.rect.right = this.point.x + 150
            this.rect.bottom = this.point.y
        }
        return pic.copy()
    }

    fun setTempXY(x: Int, y: Int) {
        var index = 0
        setTemp()
        tempSet.forEach {
            when (it.type) {
                InputType.RECTANGLE -> {
                    setTempRectXY(it, x, y)
                }
                InputType.PICTURE -> {
                    setTempPicXY(it, x, y)
                }
            }
            index++
        }
    }

    private fun setTemp() {
        if (tempSet.isEmpty()) {
            drawType.filter { it.click }.forEach {
                tempSet.add(it.copy())
            }
        }
    }

    fun resetTemp() {
        tempSet.clear()
    }

    private fun setTempRectXY(rectangle: Type, x: Int, y: Int) {
        with(rectangle) {
            val resultX = (rect.left + (size.width / 2)) + x
            val resultY = (rect.top - (size.height / 2)) + y
            rect.left = resultX - (size.width / 2)
            rect.top = resultY + (size.height / 2)
            rect.right = resultX + (size.width / 2)
            rect.bottom = resultY - (size.height / 2)
        }
    }

    private fun setTempPicXY(pic: Type, x: Int, y: Int) {
        with(pic) {
            point.x += x
            point.y += y
            rect.left = point.x
            rect.top = point.y + 120
            rect.right = point.x + 150
            rect.bottom = point.y
        }
    }

    fun setStrokeClean() {
        for (i in 0 until drawType.size) {
            drawType[i].click = false
        }
    }

    private fun initStroke() {
        stroke = Paint().apply {
            color = Color.BLUE
            strokeWidth = 4F
            style = Paint.Style.STROKE
        }
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
            if (it.rect.checkContains(pointF.x.toInt(), pointF.y.toInt())) {
                drawType[count].click = true
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

    fun changeAlpha(index: Int, alpha: Int) {
        paints[index].alpha = alpha * 25
        invalidate()
    }

    fun setPositionValue(index: Int, XValue: TextView, YValue: TextView) {
        val x = drawType[index].rect.left + (drawType[index].size.width / 2)
        val y = drawType[index].rect.top - (drawType[index].size.height / 2)
        XValue.text = x.toString()
        YValue.text = y.toString()
    }

    fun setTempPositionValue(XValue: TextView, YValue: TextView) {
        val x = tempSet.last().rect.left + (tempSet.last().size.width / 2)
        val y = tempSet.last().rect.top - (tempSet.last().size.height / 2)
        XValue.text = x.toString()
        YValue.text = y.toString()
    }

    fun setSizeValue(index: Int, width: TextView, height: TextView) {
        width.text = drawType[index].size.width.toString()
        height.text = drawType[index].size.height.toString()
    }

    fun setTempSizeValue(width: TextView, height: TextView) {
        width.text = tempSet.last().size.width.toString()
        height.text = tempSet.last().size.height.toString()
    }
}