package com.example.drawingapp

class Quadrangle(
    private val rectNumber: Int,
    private val id: String,
    private val point: Point,
    private val size: Size,
    private val color: Color,
    private val alpha: Int
) {
    override fun toString() =
        "Rect${rectNumber} (${id}), X:${point.x},Y:${point.y}, W${size.width}, H${size.height}, " +
                "R:${color.red}, G:${color.green}, B:${color.blue}, Alpha: $alpha"
}