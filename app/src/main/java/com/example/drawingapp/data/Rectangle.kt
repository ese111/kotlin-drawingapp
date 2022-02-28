package com.example.drawingapp.data

class Rectangle(
    private val rectNumber: Int,
    private val rectangleIdData: String,
    private val rectanglePoint: RectanglePoint,
    private val rectangleSize: RectangleSize,
    private val rectangleColor: RectangleColor,
    private val alpha: Int
) {
    override fun toString() =
        "Rect${rectNumber} (${rectangleIdData}), X:${rectanglePoint.x},Y:${rectanglePoint.y}, W${rectangleSize.width}, H${rectangleSize.height}, " +
                "R:${rectangleColor.red}, G:${rectangleColor.green}, B:${rectangleColor.blue}, Alpha: $alpha"
}