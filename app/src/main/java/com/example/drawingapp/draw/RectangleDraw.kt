package com.example.drawingapp.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drawingapp.data.Text
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

    private lateinit var downPointF: PointF

    private lateinit var upPointF: PointF

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
        when (type.type) {
            InputType.RECTANGLE -> setTempRect(type)
            InputType.PICTURE -> setTempPic(type)
            InputType.TEXT -> setTempText(type)
        }

        val tempStroke = Paint().apply {
            color = Color.BLUE
            strokeWidth = 4F
            style = Paint.Style.STROKE
            alpha = 5
        }

        mCanvas.drawRect(type.rect, tempStroke)
    }

    private fun setTempText(type: Type) {
        val text = type as Text
        val paint = Paint().apply {
            color = Color.argb(
                text.alpha * 25 / 2,
                text.color.red,
                text.color.green,
                text.color.blue
            )
            textSize = text.textSize
        }

        mCanvas.drawText(
            text.value,
            text.point.x.toFloat(),
            text.point.y.toFloat(),
            paint
        )

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
        when (drawType[index].type) {
            InputType.RECTANGLE -> drawRect(index)
            InputType.PICTURE -> drawPic(index)
            InputType.TEXT -> drawText(index)
        }

        if (drawType[index].click) {
            mCanvas.drawRect(drawType[index].rect, stroke)
        }
    }

    private fun drawText(index: Int) {
        val text = drawType[index] as Text
        paints[index].let { paint ->

            paint.textSize = text.textSize

            mCanvas.drawText(
                text.value,
                text.point.x.toFloat(),
                text.point.y.toFloat(),
                paint
            )
        }
        Logger.d("${text.point.x.toFloat()}, ${text.point.y.toFloat()}")
    }

    private fun drawRect(index: Int) {
        paints[index].let { paint ->
            mCanvas.drawRect(
                drawType[index].rect,
                paint
            )
        }
    }

    private fun drawPic(index: Int) {
        val pic = drawType[index] as Picture
        paints[index].let { paint ->
            mCanvas.drawBitmap(
                pic.bitmap,
                pic.point.x.toFloat(),
                pic.point.y.toFloat(),
                paint
            )
        }
    }

    fun setXY(x: Int, y: Int): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            startChangePoint(it, x, y).let { it1 -> setTypeList.add(it1) }
            index++
        }
        return setTypeList
    }

    fun setUpX(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkUpXRange(it)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownX(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkDownXRange(it)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setUpY(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkUpYRange(it)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownY(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach {
            checkDownYRange(it)?.let { it1 -> setTypeList.add(it1) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    private fun checkUpXRange(rect: Type) =
        when (rect.point.x >= 2230) {
            true -> null
            false -> startChangePoint(rect, 1, 0)
        }

    private fun checkDownXRange(rect: Type) =
        when (rect.point.x <= 70) {
            true -> null
            false -> startChangePoint(rect, -1, 0)
        }

    private fun checkUpYRange(rect: Type) =
        when (rect.point.y >= 1500) {
            true -> null
            false -> startChangePoint(rect, 0, -1)
        }

    private fun checkDownYRange(rect: Type) =
        when (rect.point.y <= 60) {
            true -> null
            false -> startChangePoint(rect, 0, 1)
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
            InputType.TEXT -> {
                checkClick(rect, x, y) { checkedText, pointX, pointY ->
                    changeTextPoint(checkedText, pointX, pointY)
                }
            }
        }

    private fun changeTextPoint(checkedText: Type, pointX: Int, pointY: Int) {
        with(checkedText) {
            point.x += pointX
            point.y += pointY
            rect.left = point.x
            rect.top = point.y - size.height
            rect.right = point.x + size.width
            rect.bottom = point.y
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
        with(pic) {
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

    fun setUpWidth(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeUpWidthRange(rect)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownWidth(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeDownWidthRange(rect)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setUpHeight(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeUpHeightRange(rect)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    fun setDownHeight(): List<Type> {
        var index = 0
        val setTypeList = mutableListOf<Type>()
        drawType.forEach { rect ->
            checkSizeDownHeightRange(rect)?.let { checkedRect -> setTypeList.add(checkedRect) }
                ?: main.showSnackBar("더 이상 크기를 조정 할 수 없습니다")
            index++
        }
        return setTypeList
    }

    private fun checkSizeUpWidthRange(rect: Type) =
        when (rect.size.width >= 2200) {
            true -> null
            false -> startChangeSize(rect, 1, 0)
        }

    private fun checkSizeDownWidthRange(rect: Type) =
        when (rect.size.width <= 1) {
            true -> null
            false -> startChangeSize(rect, -1, 0)
        }

    private fun checkSizeUpHeightRange(rect: Type) =
        when (rect.size.height >= 1400) {
            true -> null
            false -> startChangeSize(rect, 0, 1)
        }

    private fun checkSizeDownHeightRange(rect: Type) =
        when (rect.size.height <= 1) {
            true -> null
            false -> startChangeSize(rect, 0, -1)
        }

    private fun startChangeSize(rect: Type, x: Int, y: Int) =
        when (rect.type) {
            InputType.RECTANGLE -> {
                setRectSize(rect, x, y)
            }

            InputType.PICTURE -> {
                setPicSize(rect, x, y)
            }

            InputType.TEXT -> {
                setTextSize(rect, x)
            }
        }

    private fun setTextSize(rect: Type, sizeValue: Int): Type {
        rect.takeIf { it.click }?.apply {
            resizeText(rect, sizeValue)
        }
        return rect
    }

    private fun resizeText(text: Type, sizeValue: Int) {
        text as Text
        val valueSize = Text.getSize(text.value, text.textSize + 1)
        val textRect = Text.getRect(text.point, valueSize)
        with(text) {
            textSize += sizeValue
            size.width = valueSize.width
            size.height = valueSize.height
            rect.left = textRect.left
            rect.top = textRect.top
            rect.right = textRect.right
            rect.bottom = textRect.bottom
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
                InputType.TEXT -> {
                    setTempTextXY(it, x, y)
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
            rect.top = point.y + size.height
            rect.right = point.x + size.width
            rect.bottom = point.y
        }
    }

    private fun setTempTextXY(text: Type, pointX: Int, pointY: Int) {
        with(text) {
            point.x += pointX
            point.y += pointY
            rect.left = point.x
            rect.top = point.y - size.height
            rect.right = point.x + size.width
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
            if (it.rect.checkContains(pointF.x.toInt(), pointF.y.toInt())
                || it.rect.contains(pointF.x.toInt(), pointF.y.toInt())
            ) {
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

    fun setSidebarRectInfoText(
        XValue: TextView,
        YValue: TextView,
        width: TextView,
        height: TextView
    ) {
        when (drawType[getClickRectangle].type) {
            InputType.TEXT -> {
                setPositionValue(XValue, YValue)
                setTextSize(width, height)
            }
            else -> {
                setPositionValue(XValue, YValue)
                setSizeValue(width, height)
            }
        }
    }

    private fun setTextSize(width: TextView, height: TextView) {
        val text = drawType[getClickRectangle] as Text
        width.text = text.textSize.toString()
        height.text = ""
    }

    private fun setPositionValue(XValue: TextView, YValue: TextView) {
        XValue.text = drawType[getClickRectangle].point.x.toString()
        YValue.text = drawType[getClickRectangle].point.y.toString()
    }

    private fun setSizeValue(width: TextView, height: TextView) {
        width.text = drawType[getClickRectangle].size.width.toString()
        height.text = drawType[getClickRectangle].size.height.toString()
    }

    fun setTextWhenMovingRect(
        XValue: TextView,
        YValue: TextView,
        width: TextView,
        height: TextView
    ) {
        setTempPositionValue(XValue, YValue)
        when (drawType[getClickRectangle].type) {
            InputType.TEXT -> {
                setTempTextSizeValue(width, height)
            }
            else -> {
                setTempSizeValue(width, height)
            }
        }
    }

    private fun setTempPositionValue(XValue: TextView, YValue: TextView) {
        XValue.text = getRectX(tempSet.last().rect.left, tempSet.last().size.width).toString()
        YValue.text = getRectY(tempSet.last().rect.top, tempSet.last().size.height).toString()
    }

    private fun setTempSizeValue(width: TextView, height: TextView) {
        width.text = tempSet.last().size.width.toString()
        height.text = tempSet.last().size.height.toString()
    }

    private fun setTempTextSizeValue(width: TextView, height: TextView) {
        val text = tempSet.last() as Text
        width.text = text.textSize.toString()
        height.text = ""
    }

    override fun performClick(): Boolean {
        return true
    }
}