package com.example.drawingapp.data

import com.example.drawingapp.InputFactory
import com.example.drawingapp.util.generateRandom
import java.util.concurrent.CountDownLatch
import kotlin.random.Random

class RectangleInput(
    override val count: Int,
    override val randomId: String = generateRandom(),
    override val pointX: Int = Random.nextInt(800),
    override val pointY: Int = Random.nextInt(800),
    override val colorR: Int = Random.nextInt(255),
    override val colorG: Int = Random.nextInt(255),
    override val colorB: Int = Random.nextInt(255),
    override val alpha: Int = Random.nextInt(10)
) : InputFactory