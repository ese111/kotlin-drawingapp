package com.example.drawingapp.data.attribute

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import com.example.drawingapp.data.Type
import com.example.drawingapp.data.input.InputType

data class Picture(
    private val number: Int,
    private val id: String,
    val bitmap: Bitmap,
    override val point: Point,
    override var alpha: Int,
    override val size: Size,
    override val rect: Rect,
    override val type: InputType = InputType.PICTURE,
    override var click: Boolean = false
) : Type {

    override fun toString() =
        "Rect${number} (${id}), X:${rect.top},Y:${rect.bottom}, W${rect.right}, H${rect.left}, " +
                "bitmap:${bitmap}, Alpha: $alpha"

}