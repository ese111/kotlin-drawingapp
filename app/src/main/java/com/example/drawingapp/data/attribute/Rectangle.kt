package com.example.drawingapp.data.attribute

import android.graphics.Point
import android.graphics.Rect
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.input.InputType

class Rectangle(
    private val rectNumber: Int,
    private val rectangleId: String,
    private val rectanglePoint: Point,
    private val size: Size,
    val color: Color,
    private var alpha: Int,
    override val rect: Rect,
    override val type: InputType = InputType.RECTANGLE
    ) : Type  {
    override fun setAlpha(value: Int) {
        alpha = value
    }

    override fun getAlpha() = alpha

    override fun toString() =
        "Rect${rectNumber} (${rectangleId}), X:${rectanglePoint.x},Y:${rectanglePoint.y}, W${size.width}, H${size.height}, " +
                "R:${color.red}, G:${color.green}, B:${color.blue}, Alpha: $alpha"

}