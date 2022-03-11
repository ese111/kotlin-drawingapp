package com.example.drawingapp.data.attribute

import android.graphics.Point
import android.graphics.Rect
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.input.InputType

class Rectangle(
    private val rectNumber: Int,
    private val rectangleId: String,
    private val rectanglePoint: Point,
    val color: Color,
    override var alpha: Int,
    override val size: Size,
    override val rect: Rect,
    override val type: InputType = InputType.RECTANGLE,
    override var click: Boolean = false,
    override val point: Point = Point(rect.left, rect.bottom)
) : Type  {


    override fun toString() =
        "Rect${rectNumber} (${rectangleId}), X:${rectanglePoint.x},Y:${rectanglePoint.y}, W${size.width}, H${size.height}, " +
                "R:${color.red}, G:${color.green}, B:${color.blue}, Alpha: $alpha"

}