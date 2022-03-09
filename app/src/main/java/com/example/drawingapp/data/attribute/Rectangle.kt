package com.example.drawingapp.data.attribute

import android.graphics.Rect
import com.example.drawingapp.data.Type

class Rectangle(
    private val rectNumber: Int,
    private val rectangleId: String,
    private val rectanglePoint: RectanglePoint,
    private val rectangleSize: RectangleSize,
    val rectangleColor: RectangleColor,
    private var alpha: Int,
    override val rect: Rect,
    override val type: String = "RECTANGLE"
    ) : Type  {
    override fun setAlpha(value: Int) {
        alpha = value
    }

    override fun getAlpha() = alpha

    override fun toString() =
        "Rect${rectNumber} (${rectangleId}), X:${rectanglePoint.x},Y:${rectanglePoint.y}, W${rectangleSize.width}, H${rectangleSize.height}, " +
                "R:${rectangleColor.red}, G:${rectangleColor.green}, B:${rectangleColor.blue}, Alpha: $alpha"

}