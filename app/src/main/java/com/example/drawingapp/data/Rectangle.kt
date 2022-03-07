package com.example.drawingapp.data

class Rectangle(
    private val rectNumber: Int,
    private val rectangleId: String,
    val rectanglePoint: RectanglePoint,
    val rectangleSize: RectangleSize,
    val rectangleColor: RectangleColor,
    private var alpha: Int
) {
    fun setAlpha(value: Int) {
        alpha = value
    }

    fun getAlpha() = alpha

    override fun toString() =
        "Rect${rectNumber} (${rectangleId}), X:${rectanglePoint.x},Y:${rectanglePoint.y}, W${rectangleSize.width}, H${rectangleSize.height}, " +
                "R:${rectangleColor.red}, G:${rectangleColor.green}, B:${rectangleColor.blue}, Alpha: $alpha"

}