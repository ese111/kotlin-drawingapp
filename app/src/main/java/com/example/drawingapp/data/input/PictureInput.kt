package com.example.drawingapp.data.input

import android.graphics.Rect
import com.example.drawingapp.InputFactory
import com.example.drawingapp.util.generateRandom
import kotlin.random.Random

class PictureInput(
    override val count: Int,
    override val randomId: String = generateRandom(),
    override val pointX: Int = Random.nextInt(2000),
    override val pointY: Int = Random.nextInt(1500),
    override val colorR: Int = Random.nextInt(255),
    override val colorG: Int = Random.nextInt(255),
    override val colorB: Int = Random.nextInt(255),
    override val alpha: Int = Random.nextInt(10)
) : InputFactory {

    override fun getRect(): Rect {
        return Rect(pointX, pointY + 120, pointX + 150, pointY)
    }

}