package com.example.drawingapp.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.attribute.Picture
import com.example.drawingapp.data.attribute.Rectangle
import com.example.drawingapp.data.input.InputType
import com.example.drawingapp.util.showSnackBar
import com.orhanobut.logger.Logger

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

    private lateinit var main: ConstraintLayout

    fun initView(view: View) {
        main = view as ConstraintLayout
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

//    fun getClickRectangle() = getClickRectangle

    fun setXY(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            startChangePoint(it, x, y)?.let { it1 -> setTypeList.add(it1) }
            index++
        }
        return setTypeList
    }

    fun setUpX(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkUpXRange(it, x, y)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownX(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkDownXRange(it, x, y)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setUpY(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkUpYRange(it, x, y)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownY(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkDownYRange(it, x, y)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    private fun checkUpXRange(rect: Type, x: Int, y: Int) =
        when (rect.point.x >= 2230) {
            true -> null
            false -> startChangePoint(rect, x, y)
        }

    private fun checkDownXRange(rect: Type, x: Int, y: Int) =
        when (rect.point.x <= 70) {
            true -> null
            false -> startChangePoint(rect, x, y)
        }

    private fun checkUpYRange(rect: Type, x: Int, y: Int) =
        when (rect.point.y >= 1500) {
            true -> null
            false -> startChangePoint(rect, x, y)
        }

    private fun checkDownYRange(rect: Type, x: Int, y: Int) =
        when (rect.point.y <= 60) {
            true -> null
            false -> startChangePoint(rect, x, y)
        }


    private fun startChangePoint(rect: Type, x: Int, y: Int) =
        when (rect.type) {
            InputType.RECTANGLE -> {
                checkClick(rect, x, y) { checkedRect, pointX, pointY ->
                    changeRectPoint(checkedRect, pointX, pointY)
                }
            }
            InputType.PICTURE -> {
                checkClick(rect, x, y) { checkedPic, pointX, pointY ->
                    changePicPoint(checkedPic, pointX, pointY)
                }
            }
        }

    private fun checkClick(rect: Type, x: Int, y: Int, changeRect: (Type, Int, Int) -> Unit): Type {
        rect.takeIf { it.click }?.apply {
            changeRect(this, x, y)
        }
        return rect
    }

    private fun changeRectPoint(rectangle: Type, x: Int, y: Int) {
        with(rectangle) {
//            point.x += x
//            point.y += y
//            rect.left = getRectLeftPoint(point.x, size.width)
//            rect.top = getRectTopPoint(point.y, size.height)
//            rect.right = getRectRightPoint(point.x, size.width)
//            rect.bottom = getRectBottomPoint(point.y, size.height)
            point.x = getRectX(rect.left, size.width) + x
            point.y = getRectY(rect.top, size.height) + y
            rect.left = getRectLeftPoint(point.x, size.width)
            rect.top = getRectTopPoint(point.y, size.height)
            rect.right = getRectRightPoint(point.x, size.width)
            rect.bottom = getRectBottomPoint(point.y, size.height)
        }
    }

    private fun getRectX(left: Int, width: Int) = (left + (width / 2))

    private fun getRectY(top: Int, height: Int) = (top - (height / 2))

    private fun getRectLeftPoint(rectX: Int, width: Int) = rectX - (width / 2)

    private fun getRectTopPoint(rectY: Int, height: Int) = rectY + (height / 2)

    private fun getRectRightPoint(rectX: Int, width: Int) = rectX + (width / 2)

    private fun getRectBottomPoint(rectY: Int, height: Int) = rectY - (height / 2)

    private fun changePicPoint(pic: Type, x: Int, y: Int) {
        with(pic){
            point.x += x
            point.y += y
            rect.left = point.x
            rect.top = getPicTop(point.y, size.height)
            rect.right = getPicRight(point.x, size.width)
            rect.bottom = point.y
        }
    }

    private fun getPicTop(picPointY: Int, height: Int) = picPointY + height

    private fun getPicRight(picPointX: Int, width: Int) = picPointX + width

    fun setUpWidth(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeUpWidthRange(rect, x, y)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownWidth(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeDownWidthRange(rect, x, y)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setUpHeight(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeUpHeightRange(rect, x, y)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownHeight(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeDownHeightRange(rect, x, y)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    private fun checkSizeUpWidthRange(rect: Type, x: Int, y: Int) =
        when (rect.size.width >= 2200) {
            true -> null
            false -> startChangeSize(rect, x, y)
        }

    private fun checkSizeDownWidthRange(rect: Type, x: Int, y: Int) =
        when (rect.size.width <= 1) {
            true -> null
            false -> startChangeSize(rect, x, y)
        }

    private fun checkSizeUpHeightRange(rect: Type, x: Int, y: Int) =
        when (rect.size.height >= 1400) {
            true -> null
            false -> startChangeSize(rect, x, y)
        }

    private fun checkSizeDownHeightRange(rect: Type, x: Int, y: Int) =
        when (rect.size.height <= 1) {
            true -> null
            false -> startChangeSize(rect, x, y)
        }

    private fun startChangeSize(rect: Type, x: Int = 0, y: Int = 0) =
        when (rect.type) {
            InputType.RECTANGLE -> {
                setRectSize(rect, x, y)
            }
            InputType.PICTURE -> {
                setPicSize(rect, x, y)
            }
        }

    private fun setRectSize(rect: Type, x: Int, y: Int): Type {
        rect.takeIf { it.click }?.apply {
            resizeRect(rect, x, y)
        }
        return rect
    }

    private fun resizeRect(rectangle: Type, x: Int, y: Int) {
        with(rectangle) {
            point.x = getRectX(rect.left, size.width) - x
            point.y = getRectY(rect.top, size.height) + y
            rect.left = getRectLeftPoint(point.x, size.width)
            rect.top = getRectTopPoint(point.y, size.height)
            size.width += x
            size.height += y
        }
    }

    private fun setPicSize(pic: Type, x: Int, y: Int): Type {
        pic.takeIf { it.click }?.apply {
            resizePic(pic, x, y)
        }
        return pic
    }

    private fun resizePic(pic: Type, x: Int, y: Int) {
        pic as Picture
        with(pic) {
            rect.left = point.x + x
            rect.bottom = point.y + y
            rect.top = getPicTop(point.y, size.height)
            rect.right = getPicRight(point.x, size.width)
            size.width += x
            size.height += y
            bitmap = Bitmap.createScaledBitmap(bitmap, size.width, size.height, true)
        }
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
                tempSet.add(it.deepCopy())
            }
        }
    }

    fun resetTemp() {
        tempSet.clear()
    }

    private fun setTempRectXY(rectangle: Type, x: Int, y: Int) {
        with(rectangle) {
            point.x = getRectX(rect.left, size.width) + x
            point.y = getRectY(rect.top, size.height) + y
            rect.left = getRectLeftPoint(point.x, size.width)
            rect.top = getRectTopPoint(point.y, size.height)
            rect.right = getRectRightPoint(point.x, size.width)
            rect.bottom = getRectBottomPoint(point.y, size.height)
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
                return getClickRectangle
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
        XValue.text = drawType[index].point.x.toString()
        YValue.text = drawType[index].point.y.toString()
    }

    fun setTempPositionValue(XValue: TextView, YValue: TextView) {
        XValue.text = getRectX(tempSet.last().rect.left, tempSet.last().size.width).toString()
        YValue.text = getRectY(tempSet.last().rect.top, tempSet.last().size.height).toString()
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