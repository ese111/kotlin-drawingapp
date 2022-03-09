package com.example.drawingapp.data.attribute

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import com.example.drawingapp.data.Type

class Picture(
    private val number: Int,
    private val id: String,
    val bitmap: Bitmap,
    val point: PointF,
    private var alpha: Int,
    override val rect: Rect,
    override val type: String = "PICTURE"
) : Type {

    override fun setAlpha(value: Int) {
        alpha = value
    }

    override fun getAlpha() = alpha

    override fun toString() =
        "Rect${number} (${id}), X:${rect.top},Y:${rect.bottom}, W${rect.right}, H${rect.left}, " +
                "bitmap:${bitmap}, Alpha: $alpha"

}