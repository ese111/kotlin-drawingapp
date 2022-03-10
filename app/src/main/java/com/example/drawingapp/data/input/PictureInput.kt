package com.example.drawingapp.data.input

import android.graphics.Rect
import com.example.drawingapp.InputFactory
import com.example.drawingapp.data.attribute.Size
import com.example.drawingapp.util.generateRandom
import kotlin.random.Random

class PictureInput(
    override val count: Int,
    override val randomId: String = generateRandom(),
    override val pointX: Int = Random.nextInt(1800),
    override val pointY: Int = Random.nextInt(1000),
    override val colorR: Int = Random.nextInt(255),
    override val colorG: Int = Random.nextInt(255),
    override val colorB: Int = Random.nextInt(255),
    override val alpha: Int = Random.nextInt(10)
) : InputFactory {

    override fun getRect(): Rect {
        return Rect(pointX, pointY + Size().height, pointX + Size().width, pointY)
    }

}