package com.example.drawingapp.data.attribute

import android.graphics.Paint
import android.graphics.Rect

data class Size(
    var width: Int = 150,
    var height: Int = 120
) {
    fun getTextSize(text: String): Size {
        val rect = Rect()
        val paint = Paint().apply {
            textSize = 120F
        }
        paint.getTextBounds(text, 0, text.length, rect)
        return Size(rect.width(), rect.height())
    }
}