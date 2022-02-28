package com.example.drawingapp.data

class RectangleData(
    private val rectNumber: Int,
    private val rectangleIdData: String,
    private val rectanglePoint: RectanglePointData,
    private val rectangleSizeData: RectangleSizeData,
    private val rectangleColorData: RectangleColorData,
    private val alpha: Int
) {
    override fun toString() =
        "Rect${rectNumber} (${rectangleIdData}), X:${rectanglePoint.x},Y:${rectanglePoint.y}, W${rectangleSizeData.width}, H${rectangleSizeData.height}, " +
                "R:${rectangleColorData.red}, G:${rectangleColorData.green}, B:${rectangleColorData.blue}, Alpha: $alpha"
}